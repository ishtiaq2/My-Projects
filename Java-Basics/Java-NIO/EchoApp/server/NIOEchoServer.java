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
public class NIOEchoServer {
    
    private final Selector selector;
    private final ServerSocketChannel listeningSocketChannel;
    int clientCounter = 0;
    
    public NIOEchoServer() throws Exception {
        selector = Selector.open();
        listeningSocketChannel = ServerSocketChannel.open();
        listeningSocketChannel.configureBlocking(false);
        listeningSocketChannel.bind(new InetSocketAddress(1024));
        listeningSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
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
        ClientHandler handler = new ClientHandler(this, clientChannel, clientCounter);
        clientChannel.register(selector, SelectionKey.OP_READ, new Client(handler));
    }
    
    private void recvFromClient(SelectionKey key) throws Exception {
        Client client = (Client) key.attachment();
        try {
            client.handler.recvMsg(key);
        } catch (IOException clientHasClosedConnection) {
            removeClient(key);
        }
    }
    
    void sendToClient(SelectionKey key) throws Exception {
        Client client = (Client) key.attachment();
        client.handler.sendMsg();
        key.interestOps(SelectionKey.OP_READ);
        //removeClient(key);
    }
    
    public void registerWriteOperation(SelectionKey key) throws Exception {
        key.interestOps(SelectionKey.OP_WRITE);
        //selector.wakeup();
    }
    
    private void removeClient(SelectionKey clientKey) throws IOException {
        Client client = (Client) clientKey.attachment();
        client.handler.disconnectClient();
        clientKey.cancel();
    }
    
    private class Client {
        private ClientHandler handler;
        
        public Client(ClientHandler handler) {
            this.handler = handler;
        }
    }
	
	public static void main(String[] args) throws Exception {
        NIOEchoServer server = new NIOEchoServer();
        server.serve();
    }
}