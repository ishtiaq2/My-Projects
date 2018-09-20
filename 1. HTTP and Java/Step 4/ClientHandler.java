import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ForkJoinPool;

public class ClientHandler implements Runnable {
    
    public ParallelHttpServer server;
    private SocketChannel clientChannel;
	private SelectionKey key;
    private ByteBuffer msgFromClient = ByteBuffer.allocate(1024);
	String response = "";
	int clientId = 0;
	
    public ClientHandler(ParallelHttpServer server, SocketChannel clientChannel) {
        this.server = server;
        this.clientChannel = clientChannel;
    }
    
	public void run() {
		try {
			String file = "<html><head><title>EchoHttpServer</title>" +
							"</head><body> Welcome to NIO Http Server <br>" +
							"Your ID: " + clientId + "</body> </html>";
							
			String responseHeader = "HTTP/1.0 200 OK\r\n" +	
							"content-type: text/html\r\n" +
							"content-length: " + file.length() + "\r\n\r\n";
			response = responseHeader + file;
			server.send(key);
		} catch(Exception e) {
		}
	}
	
	void sendMsg() throws Exception {											
		ByteBuffer msgToClient = ByteBuffer.wrap(response.getBytes());
		clientChannel.write(msgToClient);
		if (msgToClient.hasRemaining()) {
			System.out.println("Could not send message");
		}
		server.removeClient(key);
    }
    
    void recvMsg(SelectionKey key, int id) throws Exception {
        this.key = key;
		clientId = id;
        try {
          int numOfReadBytes = 0;
			numOfReadBytes = clientChannel.read(msgFromClient);
			if (numOfReadBytes ==  -1) {
				throw new IOException("Client has closed connection.");
			}	
			msgFromClient.flip();
			byte[] bytes = new byte[msgFromClient.remaining()];
			msgFromClient.get(bytes);
			String clientMsg = new String(bytes);
			System.out.println(clientMsg);
			ForkJoinPool.commonPool().execute(this);
        } catch (Exception e) {
			System.out.println("Error: " + e);
        }
    }
        
    void disconnect() throws IOException {
        clientChannel.close();
        System.out.println("Client: " + clientId + " Served");
	}
	
}