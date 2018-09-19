import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

public class EchoHttpServer {
    
    private final Selector selector;
    private final ServerSocketChannel listeningSocketChannel;
	private ByteBuffer msgFromClient = ByteBuffer.allocate(1024);
    private int clientId = 0;
    String clientMsg = "";
      
    public EchoHttpServer() throws Exception {
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
                } else if (key.isWritable()) {
                    sendToClient(key);
                }
            }
        }
    }
    
    private void startHandler(SelectionKey key) throws Exception {
		System.out.println("----------------------------------------------------------");
		/**
		 key.channel(): Returns the channel for which this key was created
		 */
		System.out.println("Client id: " + clientId);
				
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
		/**
		 clientChannel is registered with the selector, that returns a key whose interest set is set to OP_READ and also the clientChannel is attached to the key.
		 */
        clientChannel.register(selector, SelectionKey.OP_READ, clientChannel);
    }
    
    private void recvFromClient(SelectionKey key) {
		/**
		 key's attach(Obeject obj) method is used to attach an application sepecific obeject to the key and can be used retrieve that obect via the attachment() method. Only one object can be attached.
		 */
        SocketChannel clientChannel = (SocketChannel) key.attachment();
        try {
          int numOfReadBytes = 0;
			numOfReadBytes = clientChannel.read(msgFromClient);
			if (numOfReadBytes ==  -1) {
				throw new IOException("Client has closed connection.");
			}	
			msgFromClient.flip();
			byte[] bytes = new byte[msgFromClient.remaining()];
			msgFromClient.get(bytes);
			clientMsg = new String(bytes);
			System.out.println(clientMsg);			
			key.interestOps(SelectionKey.OP_WRITE);
        } catch (Exception e) {
			System.out.println("Error: " + e);
        }
    }
	
	public void sendToClient(SelectionKey key) {
		clientId++;
		try {
			Thread.currentThread().sleep(4 * 1000);
			SocketChannel clientChannel = (SocketChannel) key.attachment();
			String file = "<html><head><title>EchoHttpServer</title>" +
							"</head><body> Welcome to NIO Http Server abcd </body>" +
							"</html>";
							
			String responseHeader = "HTTP/1.0 200 OK\r\n" +	
							"content-type: text/html\r\n" +
							"content-length: " + file.length() + "\r\n\r\n";
			String response = responseHeader + file;
														
			ByteBuffer msgToClient = ByteBuffer.wrap(response.getBytes());
			clientChannel.write(msgToClient);
			removeClient(key);
		} catch(Exception e) {}
	}
      
    public void removeClient(SelectionKey key) throws Exception {
		SocketChannel clientChannel = (SocketChannel) key.attachment();
        clientChannel.close();
        key.cancel();//this is not needed as the key becomes unvalid on closing its channel
    }
        
	public static void main(String[] args) throws Exception {
        EchoHttpServer server = new EchoHttpServer();
        server.serve();
    }
}