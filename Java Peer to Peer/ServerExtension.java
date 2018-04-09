import java.io.*;
import java.net.*;

public class ServerExtension implements Runnable {
	
	private ChatClient chatClient;
	private int port;
	
	public ServerExtension (ChatClient chatClient, String port) {
		this.chatClient = chatClient;
		this.port = Integer.parseInt(port);
	}
	
	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Mini Server Listenting on port: " + port);
			Socket client = server.accept();
			System.out.println("peer connected");
			PrintWriter out = new PrintWriter(client.getOutputStream());
			System.out.println("Uploading file...");
			
			
			String file = "img0.jpg";
			File f = new File(file);
			double fsize = f.length();
			out.println((int)fsize);
			out.flush();
			
			sendFile(f, client, (int)fsize);
			
			System.out.println("UPloading complete");
			client.close();
			chatClient.serverExtension = false;
		} catch (Exception e) {
			System.out.println("ServerExtension error: " + e);
		}
	}
	
	public void sendFile(File file, Socket client, int fsize) throws IOException {
		
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		
		byte[] buffer = new byte[fsize];
		
		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}
		
		fis.close();
		dos.close();	
	}
}