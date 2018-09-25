import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CompletableFuture;
import java.io.*;

public class ClientHandler implements Runnable {
    
    public ParallelHttpServer server;
    protected SocketChannel clientChannel;
	private SelectionKey key;
	private CounterResources resources;
	private ExecutorService pool;
    private ByteBuffer msgFromClient;
	private File[] response = new File[2];
	private String clientMsg = "";
	int clientId = 0;
	long startTime;
	
    public ClientHandler(ParallelHttpServer server, SocketChannel clientChannel, int clientId, CounterResources resources) {
		startTime = System.currentTimeMillis();
        this.server = server;
        this.clientChannel = clientChannel;
		this.resources = resources;
		pool = Executors.newFixedThreadPool(1);
		msgFromClient = ByteBuffer.allocate(1024);
		this.clientId = clientId;
    }
    
	public void run() {
		try {
			response /**file */ = resources.processRequest(clientMsg, clientId);
			
			sendMsg(response);
		} catch(Exception e) {
		}
	}
	
	void sendMsg(File[] res) {
		
		//CompletableFuture.runAsync(()-> { 
		/** As above causes to sendMsg in a different thread,   and at the same time the  
		 keeping the main thrad to opearate on the key. that results in closedChannel exception
		*/
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			for (File f: res) {	
				FileInputStream inFile = new FileInputStream(f);
				while(inFile.available() > 0 ) {
					inFile.getChannel().read(buffer);
					buffer.flip();
					clientChannel.write(buffer);
					buffer.clear();
				}
				inFile.close();
			}
			
				
		} catch(Exception e){
			System.out.println("Error while sendMsg: " + e);
		}
		//}, pool).thenRun(() -> System.out.println("messgage sent"));
		System.out.println(res[1].hashCode());
		pool.shutdown();
		server.removeClient(key);
			
		
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
			clientMsg = new String(bytes);
			pool.execute(this);
        } catch (Exception e) {
			System.out.println("Error while recvMsg: " + e);
        }
    }
        
    void disconnect(){
		try {
			clientChannel.close();
			System.out.println("Client: " + clientId + " Served in " + 
				(System.currentTimeMillis() - startTime) +" milli seonds");
		} catch(Exception e) {
			System.out.println("Erro while disconnect: " + e);
		}
	}
	
}