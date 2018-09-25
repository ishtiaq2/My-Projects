import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ParallelHttpServer {
    
    private final Selector selector;
    private final ServerSocketChannel listeningSocketChannel;
	private ByteBuffer msgFromClient = ByteBuffer.allocate(1024);
	private int clientId = 0;
	private CounterResources resources;
	private CounterService service;
          
    public ParallelHttpServer() throws Exception {
        selector = Selector.open();
        listeningSocketChannel = ServerSocketChannel.open();
        listeningSocketChannel.configureBlocking(false);
        listeningSocketChannel.bind(new InetSocketAddress(8080));
		/**
		 The listeningSocketChannel is registered with the selector that result in 
		 returning a SelectionKey object. Also the interest set of the key is
		 initially set to int value (SelectionKey.OP_ACCEPT), and it can be changed 
		 later via the interestOps(int).
		 SelectionKey also has a ready set, initially set to zero, however, this is operated(updated, during a selection operation) only by the selector to see
		 if the key's channel is ready.		 
		 */
        listeningSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		service = new CounterService();
		resources = new CounterResources(service);
    }
		
    private void serve() throws Exception {
		System.out.println("Server Listening on Port 8080");
		while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    startHandler(key);
                } else if (key.isReadable()) {
                    recvFromClient(key);
                } /*else if (key.isWritable()) {
                    sendToClient(key);
                }*/
            }
        }
    }
    
    private void startHandler(SelectionKey key) throws Exception {
		System.out.println("----------------------------------------------------------");
		/**
		 key.channel(): Returns the channel for which this key was created
		 */
		synchronized (this) {
			clientId++;
		}
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
		/**
		 clientChannel is registered with the selector, that returns a key whose interest set is set to OP_READ and also the clientChannel is attached to the key.
		 */
		ClientHandler handler = new ClientHandler(this, clientChannel, clientId, resources);
        clientChannel.register(selector, SelectionKey.OP_READ, handler);
		//key.attach(handler);
    }
    
	private void recvFromClient(SelectionKey key) {
		/**
		 key's attach(Obeject obj) method is used to attach an application sepecific obeject to the key and can be used retrieve that obect via the attachment() method. Only one object can be attached.
		 */
       try {
			ClientHandler handler = (ClientHandler) key.attachment();
			handler.recvMsg(key);
		} catch(Exception e) {
			System.out.println("Error while recvFromClient: " + e);
		}
    }
	
	/** This is part of the code, as well as sendMsg part of the handler will execute in main thread,
	 and hence could cause delay (creates a bottleneck and hence need to be executed by a separate thread);
	 */
	public void sendToClient(SelectionKey key) {
		try {
			ClientHandler handler = (ClientHandler) key.attachment();
			//handler.sendMsg();
			
		} catch (Exception e) {
			System.out.println("Error while sendToClient: " + e);
		}
	}
    
	public void registerWriteOperation(SelectionKey key) {
		key.interestOps(SelectionKey.OP_WRITE);
	}
    public void removeClient(SelectionKey key) {
		try {
			ClientHandler handler = (ClientHandler) key.attachment();
			key.cancel();
			handler.disconnect();
			//this is not needed as the key becomes unvalid on closing its channel
		} catch(Exception e) {
			System.out.println("Error in removeClient: " + e);
		}
    }
        
	public static void main(String[] args) throws Exception {
        ParallelHttpServer server = new ParallelHttpServer();
        server.serve();
    }
}