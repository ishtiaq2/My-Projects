import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CompletableFuture;

public class ClientHandler implements Runnable {
    
    public ParallelHttpServer server;
    private SocketChannel clientChannel;
	private SelectionKey key;
	private ExecutorService pool;
    private ByteBuffer msgFromClient;
	String response = "";
	int clientId = 0;
	long startTime;
	
    public ClientHandler(ParallelHttpServer server, SocketChannel clientChannel, int clientId) {
		startTime = System.currentTimeMillis();
        this.server = server;
        this.clientChannel = clientChannel;
		pool = Executors.newFixedThreadPool(10);
		msgFromClient = ByteBuffer.allocate(1024);
		this.clientId = clientId;
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
			System.out.println("Thread in run: " + Thread.currentThread().getName());
			server.send(key);
		} catch(Exception e) {
		}
	}
	
	void sendMsg() throws Exception {
		CompletableFuture.runAsync(()-> {
			
			try {
				Thread.currentThread().sleep(3 * 1000);
				ByteBuffer msgToClient = ByteBuffer.wrap(response.getBytes());
				clientChannel.write(msgToClient);
				if (msgToClient.hasRemaining()) {
					System.out.println("Could not send message");
				}
				server.removeClient(key);
			} catch(Exception e){}
		}, pool).thenRun(() -> System.out.println("messgage sent"));
		pool.shutdown();
    }
    
    void recvMsg(SelectionKey key) throws Exception {
		this.key = key;
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
			//ForkJoinPool.commonPool().execute(this);
			pool.execute(this);
        } catch (Exception e) {
			System.out.println("Error: " + e);
        }
    }
        
    void disconnect() throws IOException {
        clientChannel.close();
        System.out.println("Client: " + clientId + " Served in " + 
			(System.currentTimeMillis() - startTime));
	}
	
}