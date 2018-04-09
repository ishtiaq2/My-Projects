import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	
	private final List<ClientHandler> clients = new ArrayList<>();
	
	public void broadcast(String msg) {
        //contr.appendEntry(msg);
        synchronized (clients) {
            clients.forEach((client) -> client.sendMsg(msg));
        }
	}
	
	public synchronized void unicast(String sender, String receiver, String msg) {
		ClientHandler unicastReceiver = null;
		ClientHandler unicastSender  = null;
        try {
			for (ClientHandler ch: clients) {
				String recvr = ch.getUsername();
				if (recvr.equalsIgnoreCase(receiver)) {
					unicastReceiver = ch;
				}
				if (recvr.equalsIgnoreCase(sender)) {
					unicastSender = ch;
				}
				
			}
			
			if (unicastReceiver != null ) {
				unicastReceiver.sendMsg(sender + ":" + msg);
			} else {
				unicastSender.sendMsg(receiver + " does not exist");
			}
		} catch (Exception e) {
			System.out.println("Server unicast error: " + e);
		}
        
	}

	
	public void serve() {
		try {
			ServerSocket listeningSocket = new ServerSocket(1024);
			FileHandler fileHandler = new FileHandler(this);
			Thread fileHandlerThread = new Thread(fileHandler);
			fileHandlerThread.start();
			
			System.out.println("Server Listening on port 1024");
			
			while (true) {
				Socket client = listeningSocket.accept();
				handleConnection(client, fileHandler);
			}
		} catch (Exception e) {
			System.out.println("Server Error");
		}
			
	}
	
	public void handleConnection(Socket client, FileHandler fileHandler) {
		try {
			ClientHandler handler = new ClientHandler(this, client, fileHandler);
			synchronized (clients) {
				clients.add(handler);
			}
			Thread handlerThread = new Thread(handler);
			handlerThread.start();
		} catch (Exception e) {
			System.out.println("Handle Connection Error");
		}
	}
	
	void removeHandler(ClientHandler handler) {
        synchronized (clients) {
            clients.remove(handler);
        }
	}
	
	public List<ClientHandler> getClientList() {
		return clients;
	}
	
	public static void main(String[] args) {
		ChatServer server = new ChatServer();
		server.serve();
	}
}