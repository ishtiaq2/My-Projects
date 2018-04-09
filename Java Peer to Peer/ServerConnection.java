import java.io.*;
import java.net.*;
import java.util.*;

public class ServerConnection {
	
	private static final int TIMEOUT_HALF_HOUR = 1800000;
	private static final int TIMEOUT_HALF_MINUTE = 50000;
	public Socket socket;
	private PrintWriter out;
	private BufferedReader in; 
	public boolean connected = false;
	public boolean usernameRegistered = false;
	OutputHandler outputHandler;
	
	public ServerConnection(OutputHandler outputHandler) {
		this.outputHandler = outputHandler;
	}
	
	public void connect(String host, String p) throws Exception {
		if (!connected) {
			socket = new Socket();
			int port = Integer.parseInt(p);
			socket.connect(new InetSocketAddress(host, port), TIMEOUT_HALF_MINUTE);
			socket.setSoTimeout(TIMEOUT_HALF_HOUR);
			connected = true;
			boolean autoFlush = true;
			out = new PrintWriter(socket.getOutputStream(), autoFlush);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			new ListenToServer(outputHandler).start();
			outputHandler.handleMsg("Connected to " + host +":" + p);
		}
		else {
			outputHandler.handleMsg("Already Connected!");
		}
	}
	
	public void sendUsername(String username) throws Exception{
		if (!usernameRegistered) {
			sendMsg("USER", username);
			usernameRegistered = true;
		} else {
			outputHandler.handleMsg("Already Registered");
		}
	}
	
	 private void sendMsg(String... parts) throws Exception {
        StringJoiner joiner = new StringJoiner(":");
        for (String part : parts) {
            joiner.add(part);
        }
        out.println(joiner.toString());
	}
	
	
	public void sendEnteredLine(String line) throws Exception {
		sendMsg("ENTRY", line);		
	}
	
	public void sendUnicastMessage(String receiver, String msg) throws Exception {
		sendMsg("UNICAST", receiver, msg);		
	}
	
	public void sendFileHandlingRequest(String cmd, String sno, String receiver, String file) throws Exception {
		sendMsg(cmd, sno, receiver, file );		
	}
	
	public void disconnect() throws Exception {
		sendMsg("QUIT", " left conversation");
		outputHandler.handleMsg("Connection closed");
		connected = false;
		socket.close();
		System.exit(0);
	}
	
	/**
	 * The thread responsible to handle the incoming data and display it to the user.
	 */
	
	
	private class ListenToServer extends Thread {
		OutputHandler outputHandler;
		
		public ListenToServer(OutputHandler output) {
			outputHandler = output;
		}
		
		public void run() {
			try {
				while (connected) {
					System.out.println("hello error");
					String str = in.readLine();
					
					if (str != null ) {

						outputHandler.handleMsg(str);
						
					} else {
						disconnect();
					}	
					
				}
			} catch (Exception e) {
				outputHandler.handleMsg("Connection lost");
				connected = false;
				
				try {
				disconnect();
				} catch (Exception ee) {
					System.out.println("listentoserver error: " + ee);
				}
			}
		}
		
		
	}
	
}
