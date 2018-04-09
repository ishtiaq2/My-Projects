import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.*;

public class HttpServer	{
	
	private volatile int clientId = 0;
	private final List<Client> clients = new ArrayList<>();
	
	public void serve() {
		try {
			ServerSocket ss = new ServerSocket(80);
			System.out.println("Server Listening on Port:80");
			System.out.println();
			while(true) {
				Socket s = ss.accept();
				handleRequest(s);						
			}
		} catch (Exception e) {
			System.out.println("Error in Server serve: " + e);
		}
	}
	
	public static void main(String[] args) throws IOException	{
		HttpServer server = new HttpServer();
		server.serve();	
	}
	
	public void handleRequest(Socket client) {
		try {
			RequestHandler requestHandler = new RequestHandler(this, client);
			Thread requestThread = new Thread(requestHandler);
			requestThread.start();
		} catch (Exception e) {
			System.out.println("Error in handleRequest: " + e);
		}
	}
	
	public synchronized Client createNewClient() {
		clientId = clientId + 1;
		Client client = new Client(this, clientId);
		clients.add(client);
		setRandomNumber(client);
		return client;
	}
	
	public synchronized Client getClient(int id) {
		Client client = null;
		for (Client clnt : clients) {
			if (clnt.clientId == id) {
				client = clnt;
			}
		}
		return client;
	}
	
	public synchronized void setRandomNumber(Client client) {
		Random r = new Random();
		int b = r.nextInt(100);
		if ( b < 1 ) {
			b = b + 1;
		} 
		if ( b > 99 ) {
			b = b - 1;
		}
		client.rnumber = b;
	}
}