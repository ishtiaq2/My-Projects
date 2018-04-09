import java.io.*;
import java.net.*;

public class ClientExtension implements Runnable {
	
	private ChatClient chatClient;
	private int port;
	private String ip;
	private static final int TIMEOUT_HALF_MINUTE = 50000;
	
	public ClientExtension (ChatClient chatClient, String ip, String port) {
		this.chatClient = chatClient;
		this.port = Integer.parseInt(port);
		this.ip = ip;
	}
	
	public void run() {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), TIMEOUT_HALF_MINUTE);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Downloading file...");
			String str = in.readLine();
			int fsize = Integer.parseInt(str);
			receiveFile("downloaded.jpg", socket, fsize);
			System.out.println("Downloading complete");
			
			//socket.close();
			chatClient.clientExtension = false;
		} catch (Exception e) {
			System.out.println("clientExtension error: " + e);
		}
	}
	
	private void receiveFile(String file, Socket clientSock, int fsize) throws IOException {
		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[fsize];
		
		int filesize = fsize; // Send file size in separate msg
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			totalRead += read;
			remaining -= read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}
		
		fos.close();
		dis.close();
		clientSock.close();
	}
}
