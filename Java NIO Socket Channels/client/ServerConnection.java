import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 *
 * @author ishtiaq
 */
public class ServerConnection implements Runnable {
    private final SocketChannel socketChannel;
    private final Selector selector;
	SelectionKey keyKey;
    private final boolean connected;
    private final InetSocketAddress serverAddress;
	public ShowInputFromServer output;
	public HangmanClientView view;
	private String toServer = "";
    
    public ServerConnection(HangmanClientView view) throws Exception {
		this.view = view;
        serverAddress = new InetSocketAddress("localhost", 1024);
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(serverAddress);
        connected = true;
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }
	
	public void send(String cmd, String info) throws Exception {
		toServer = cmd + ":" + info;
		registerWriteOperation(keyKey);
		selector.wakeup();
	}
    
    public void run() {
		
		try {
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
		} catch (Exception e) {
		}
    }
    
    private void completeConnection(SelectionKey key) throws Exception {
		keyKey = key;
        socketChannel.finishConnect();
        keyKey.interestOps(SelectionKey.OP_READ);
    }
    
    public void sendToServer(SelectionKey key) throws Exception {
        ByteBuffer msgToServer = ByteBuffer.wrap(toServer.getBytes());
        socketChannel.write(msgToServer);
        key.interestOps(SelectionKey.OP_READ);
        
        //socketChannel.close();
        //socketChannel.keyFor(selector).cancel();
    }
    
    public void recvFromServer(SelectionKey key) throws Exception {
		
        ByteBuffer fromServer = ByteBuffer.allocate(1024);
        socketChannel.read(fromServer);
        fromServer.flip();
        byte[] bytes = new byte[fromServer.remaining()];
        fromServer.get(bytes);
        String recvdString = new String(bytes);		
        view.show(recvdString);
		//key.interestOps(SelectionKey.OP_READ);
        //socketChannel.close();
        //socketChannel.keyFor(selector).cancel();
        //send(key);
    }
	
	public void registerWriteOperation(SelectionKey key) throws Exception {
        key.interestOps(SelectionKey.OP_WRITE);
        //selector.wakeup();
    }
}