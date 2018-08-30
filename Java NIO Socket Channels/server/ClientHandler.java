import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author ishtiaq
 */
public class ClientHandler implements Runnable {
    
    public NIOServer server;
    private SocketChannel clientChannel;
	private SelectionKey key;
	Game game;
    private ByteBuffer msgFromClient = ByteBuffer.allocate(1024);
    private int clientId;
    String clientMsg = "";
	String response = "";
    
    public ClientHandler(NIOServer server, SocketChannel clientChannel, int clientId, SelectionKey key) {
        this.server = server;
        this.clientChannel = clientChannel;
		this.clientId = clientId;
		this.key = key;
		game = new Game(this, key);
    }
    
	public void run() {
		try {
			response = game.runIt(clientMsg);
			server.send(key);
		
		} catch(Exception e) {
		}
	}
	
    void sendMsg(SelectionKey key) throws Exception {
		if (response != "" ) {
			ByteBuffer msgToClient = ByteBuffer.wrap(response.getBytes());
			clientChannel.write(msgToClient);
			if (msgToClient.hasRemaining()) {
				System.out.println("Could not send message");
			}
			server.registerReadOperation(key);
		} else {
			String res = "Hi " + clientId + "\nYour are now Connected\n" +
								"Enter [user: name] to register";
			ByteBuffer msgToClient = ByteBuffer.wrap(res.getBytes());
			clientChannel.write(msgToClient);
			if (msgToClient.hasRemaining()) {
				System.out.println("Could not send message");
			}
			server.registerReadOperation(key);
		}
    }
    
	public void registerWriteOperation(SelectionKey keyy) throws Exception {
		server.registerWriteOperation(keyy);
	}
	
    void recvMsg(SelectionKey key) throws Exception {
        msgFromClient.clear();
        int numOfReadBytes;
        numOfReadBytes = clientChannel.read(msgFromClient);
        if (numOfReadBytes == -1) {
            throw new IOException("Client has closed connection.");
        }
        msgFromClient.flip();
        byte[] bytes = new byte[msgFromClient.remaining()];
        msgFromClient.get(bytes);
        clientMsg = new String(bytes);
		this.key = key;
		ForkJoinPool.commonPool().execute(this);
    }
        
    void disconnectClient() throws IOException {
        clientChannel.close();
        System.out.println("Client Disconnected");
    }
}
