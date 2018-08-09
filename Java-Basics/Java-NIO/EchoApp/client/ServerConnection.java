import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 *
 * @author ishtiaq
 */
public class ServerConnection {
    private final SocketChannel socketChannel;
    private final Selector selector;
    private final boolean connected;
    private final InetSocketAddress serverAddress;
    
    public ServerConnection() throws Exception {
        serverAddress = new InetSocketAddress("localhost", 1024);
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(serverAddress);
        connected = true;
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }
    
    public void serve() throws Exception {
        while (connected) {
            selector.select();
                for (SelectionKey key : selector.selectedKeys()) {
                    selector.selectedKeys().remove(key);
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isConnectable()) {
                        completeConnection(key);
                    } else if (key.isReadable()) {
                        recvFromServer(key);
                    } else if (key.isWritable()) {
                        sendToServer(key);
                    }
                }
        }
    }
    
    private void completeConnection(SelectionKey key) throws Exception {
        socketChannel.finishConnect();
        key.interestOps(SelectionKey.OP_WRITE);
    }
    
    public void sendToServer(SelectionKey key) throws Exception {
        ByteBuffer msgToServer = ByteBuffer.wrap(("Hello from Client").getBytes());
        socketChannel.write(msgToServer);
        key.interestOps(SelectionKey.OP_READ);
        
        //socketChannel.close();
        //socketChannel.keyFor(selector).cancel();
    }
    
    public void recvFromServer(SelectionKey key) throws Exception {
        ByteBuffer echoMsg = ByteBuffer.allocate(1024);
        socketChannel.read(echoMsg);
        echoMsg.flip();
        byte[] bytes = new byte[echoMsg.remaining()];
        echoMsg.get(bytes);
        String recvdString = new String(bytes);
        System.out.println(recvdString);
        //socketChannel.close();
        //socketChannel.keyFor(selector).cancel();
        Thread.sleep(2000);
        key.interestOps(SelectionKey.OP_WRITE);
    }
}