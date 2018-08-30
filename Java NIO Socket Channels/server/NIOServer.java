import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 * @author ishtiaq
 */
public class NIOServer {
    
    private final Selector selector;
    private final ServerSocketChannel listeningSocketChannel;
    int clientCounter = 0;
    
    public NIOServer() throws Exception {
        selector = Selector.open();
        listeningSocketChannel = ServerSocketChannel.open();
        listeningSocketChannel.configureBlocking(false);
        listeningSocketChannel.bind(new InetSocketAddress(1024));
        listeningSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }
    
	public void send(SelectionKey key) throws Exception {
		registerWriteOperation(key);
		selector.wakeup();
	}
	
    private void serve() throws Exception {
        System.out.println("Server Listening on Port 1024");
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
                    startHandler(key, ++clientCounter);
                } else if (key.isReadable()) {
                    recvFromClient(key);
                } else if (key.isWritable()) {
                    sendToClient(key);
                }
            }
        }
    }
    
    private void startHandler(SelectionKey key, int c) throws Exception {
        System.out.println("Client connected: " + c);
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        ClientHandler handler = new ClientHandler(this, clientChannel, clientCounter, key);
        clientChannel.register(selector, SelectionKey.OP_WRITE, handler);
		//System.out.println("Thread ID: " + Thread.currentThread().getId());
    }
    
    private void recvFromClient(SelectionKey key) throws Exception {
        ClientHandler handler = (ClientHandler) key.attachment();
        try {
            handler.recvMsg(key);
        } catch (IOException clientHasClosedConnection) {
            removeClient(key);
        }
    }
    
    void sendToClient(SelectionKey key) throws Exception {
        ClientHandler handler = (ClientHandler) key.attachment();
        handler.sendMsg(key);
        //removeClient(key);
    }
    
	public void registerReadOperation(SelectionKey key) throws Exception {
        key.interestOps(SelectionKey.OP_READ);
        //selector.wakeup();
    }
	
    public void registerWriteOperation(SelectionKey key) throws Exception {
        key.interestOps(SelectionKey.OP_WRITE);
        //selector.wakeup();
    }
    
    public void removeClient(SelectionKey clientKey) throws IOException {
        ClientHandler handler = (ClientHandler) clientKey.attachment();
        handler.disconnectClient();
        clientKey.cancel();
    }
        
	public static void main(String[] args) throws Exception {
        NIOServer server = new NIOServer();
        server.serve();
    }
}