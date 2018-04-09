import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
	private ChatServer server;
	private Socket socket;
	FileHandler fileHandler;
	BufferedReader in;
	PrintWriter out;
	boolean connected = false;
	private String username = "anonymous";
	
	public ClientHandler(ChatServer server, Socket socket, FileHandler fileHandler) {
		try {
			this.server = server;
			this.socket = socket;
			this.fileHandler = fileHandler;
			boolean autoFlush = true;
			out = new PrintWriter(socket.getOutputStream(), autoFlush);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connected = true;
		} catch (Exception e) {
			System.out.println("ClientHandler error");
		}
	}
	
	public void run() {
		try {
			
			while (connected) {
				Message msgFromClient = new Message(readNextLine());
				switch (msgFromClient.getCmd()) {
					
					case "USER": 
						username = msgFromClient.getParam(0);
						server.broadcast(username + " has joined");
						break;
						
					case "ENTRY":
						server.broadcast(username + ":" + msgFromClient.getParam(0));
						break;
						
					case "UNICAST":
						server.unicast(username, msgFromClient.getParam(0), msgFromClient.getParam(1));
						break;
					
					case "SYN":
						fileHandler.handleFileRequest(createFileParams(msgFromClient));
						break;
					
					case "ACK":
						fileHandler.handleFileRequest(createFileParams(msgFromClient));
						break;
						
					case "QUIT":
						server.broadcast(username + "" + msgFromClient.getParam(0));
						disconnectClient();
						break;
					
					default:
						System.out.println("Broken message");
				}
				
			}
		} catch (Exception e) {
			System.out.println("ClientHandler run Error");
		}
	}
	
	public synchronized String[] createFileParams(Message msgFromClient) {
		String[] paras = new String[5];
		paras[0] = username;					//message sender
		paras[1] = msgFromClient.getCmd();		//SYN/ACK
		paras[2] = msgFromClient.getParam(0);	//sno
		paras[3] = msgFromClient.getParam(1);	//receiver
		paras[4] = msgFromClient.getParam(2);	//file.txt
		return 	paras;	
	}
	private String readNextLine() {
		String line = "";
		try {
			line = in.readLine();
		} catch (Exception e) {
			System.err.println("Error in readNextLine: " + e);
			disconnectClient();
		}
		return line;
    }
	
	private class Message {
		
		String cmd;
		String[] params;
		private int cmdIndex = 0;
		private String receiveddLine = "";
		
		
		public Message(String line) {
			receiveddLine = line;
			parseLine(receiveddLine);
		}
		
		public void parseLine(String line) {
			String[] tokens = line.split(":");
			cmd = tokens[cmdIndex].toUpperCase();
			extractParams(tokens);
		}
		
		public void extractParams(String[] tokens) {
			params = new String[tokens.length -1];
			for (int i = 0; i < params.length ; i++) {
				params[i] = tokens[i+1];
			}
		}
		
		public String getCmd() {
			return cmd;
		}
		
		public String getParam(int index) {
			return params[index];
		}
		
		public String getReceivedLine() {
			return receiveddLine;
		}
	}
	
	public void sendMsg(String msg) {
        try {
			out.println(msg);
		} catch (Exception e) {
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public Socket getSocket() {
		return socket;
	}
	private void disconnectClient() {
        try {
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        connected = false;
        server.removeHandler(this);
	}
	
}