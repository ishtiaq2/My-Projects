import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class HttpServer {
    
    private final Selector selector;
    private final ServerSocketChannel listeningSocketChannel;
	private ByteBuffer msgFromClient = ByteBuffer.allocate(1024);
    private int clientId;
    String clientMsg = "";
      
    public HttpServer() throws Exception {
        selector = Selector.open();
        listeningSocketChannel = ServerSocketChannel.open();
        listeningSocketChannel.configureBlocking(false);
        listeningSocketChannel.bind(new InetSocketAddress(8080));
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
                    //sendToClient(key);
                }
            }
        }
    }
    
    private void startHandler(SelectionKey key) throws Exception {
		System.out.println("----------------------------------------------------------");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ, clientChannel);
    }
    
    private void recvFromClient(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.attachment();
        try {
          int numOfReadBytes = 0;
			numOfReadBytes = clientChannel.read(msgFromClient);
			if (numOfReadBytes == -1) {
				throw new IOException("Client has closed connection.");
			}	
			msgFromClient.flip();
			byte[] bytes = new byte[msgFromClient.remaining()];
			msgFromClient.get(bytes);
			clientMsg = new String(bytes);
			System.out.println(clientMsg);
			removeClient(key);
        } catch (Exception e) {
			System.out.println("Error: " + e);
        }
    }
      
    public void removeClient(SelectionKey key) throws Exception {
		SocketChannel clientChannel = (SocketChannel) key.attachment();
        clientChannel.close();
        key.cancel();
    }
        
	public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        server.serve();
    }
}