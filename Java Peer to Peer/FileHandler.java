import java.io.*;
import java.net.*;
import java.util.*;

public class FileHandler implements Runnable {
	
	ChatServer server;
	private final List<Peer> peers = new ArrayList<>();
	private volatile boolean received = false;
	private String fileRequest = "";
	int sno = 1;
	
	public FileHandler(ChatServer server) {
		this.server = server;
	}
	
	public void run() {
		while (true) {
			if (received) {
				String s = getFileRequest();
				System.out.println(s);
				received = false;
			}
		}
	}
	
	//params = sender, syn/ack, sno, rec, file
	public synchronized void handleFileRequest(String[] params) {
		int tempsno = Integer.parseInt(params[2]);
		if (tempsno == 0) {
			if (params[1].equalsIgnoreCase("SYN")) {
				createPeer(params);
			} else {
				System.out.println("SYN/ACK: Invalid command");
			}
		} else if (tempsno > 0) {
			analyseACKNACK(params, tempsno);
		} else {
			System.out.println("invalid file handling input");
		}
	}
	
	public void createPeer(String[] params) {
		String fileSender = getFileSender(params[0]);
		String fileReceiver = getFileReceiver(params[3]);
		String senderIP = getFileSenderIP(params[0]);
		
		String file = params[4];
		
		if (fileReceiver.length() > 0 ) {
			Peer peer = new Peer(sno, fileSender, fileReceiver, file, senderIP);
			System.out.println(peer);
			peers.add(peer);
			server.unicast(peer.getSender(), peer.getReceiver(), " want to Send: " +
							peer.getFile() + " with sno: " + peer.getSno());
			sno++;
		} else {
			server.unicast(params[0], params[3], "temp");
		}
		
	}
	
	public void analyseACKNACK(String[] params, int tempsno) {
		Peer peer = null;
		try {
			for (Peer p: peers ) {
				int sn = p.getSno();
				if (sn == tempsno) {
					peer = p;
				}
			}
			
			if ( params[1].equalsIgnoreCase("ACK") ) {
				if ( !peer.getACK() ) {
					if (peer.getReceiver().equalsIgnoreCase(params[0]) ) {
						peer.setACK(true);
						sendUnicastMessage(peer, "ack");
					} else {
						System.out.println(params[3]);
						System.out.println("here garbage");
					}
				} else if ( peer.getACK() ) {
					if (peer.getSender().equalsIgnoreCase(params[0])) {
						peer.setPort(params[4]);
						peer.setReady(true);
						sendReadyMessage(peer);
						peers.remove(peer);
					} else {
						System.out.println("garbage");
					}
				}
			} else if ( params[1].equalsIgnoreCase("NACK") ) {
				sendUnicastMessage(peer, "nack");
				peers.remove(peer);
			}
			
			System.out.println(peer);
		} catch (Exception e) {
			System.out.println("No Such peering: " + e);
		}
		
	}
	
	public void sendUnicastMessage(Peer peer, String acknack) {
		if (acknack.equalsIgnoreCase("ACK")) {
			try {
				server.unicast(peer.getReceiver(), peer.getSender(), " Accept to receiver: " +
							peer.getFile() + " with sno: " + peer.getSno());
			} catch (Exception e) {
				System.out.println("sendUnicastMessage error: " + e);
			}
		} else if (acknack.equalsIgnoreCase("NACK")) {
			try {
				server.unicast(peer.getReceiver(), peer.getSender(), " Declined to receive: " +
							peer.getFile() + " with sno: " + peer.getSno());
			} catch (Exception e) {
				System.out.println("sendUnicastMessage error: " + e);
			}
		}
			
	}
		
	public void sendReadyMessage(Peer peer) {
		try {
			server.unicast(peer.getSender(), peer.getReceiver(), " Connect to IP: " +
							peer.getIP() + ":" + peer.getPort());
		} catch (Exception e) {
			System.out.println("sendReadyMessage error: " + e);
		}
	}
	
	public String getFileSender(String sender) {
		String fileSender = "";
        try {
			List<ClientHandler> clients = server.getClientList();
			for (ClientHandler ch: clients) {
				String sendr = ch.getUsername();
				if (sendr.equalsIgnoreCase(sender)) {
					fileSender = sender;
				}
			}
		} catch (Exception e) {
			System.out.println("getFileSender Error: " + e);
		}
		return fileSender;
	}
	
	public String getFileReceiver(String receiver) {
		String fileReceiver = "";
        try {
			List<ClientHandler> clients = server.getClientList();
			for (ClientHandler ch: clients) {
				String recvr = ch.getUsername();
				if (recvr.equalsIgnoreCase(receiver)) {
					fileReceiver = receiver;
				}
			}
		} catch (Exception e) {
			System.out.println("getFileSender Error: " + e);
		}
		return fileReceiver;
	}
	
	public synchronized void setFileRequest(String msg) {
		sno++;
		fileRequest = "Request " + sno + ":" + msg ;
		received = true;
	}
	
	public String getFileSenderIP(String sender) {
		String senderIP = "";
		ClientHandler chandler = null;
		
        try {
			List<ClientHandler> clients = server.getClientList();
			for (ClientHandler ch: clients) {
				String sendr = ch.getUsername();
				if (sendr.equalsIgnoreCase(sender)) {
					chandler = ch;
					Socket s = ch.getSocket();
					senderIP = s.getRemoteSocketAddress().toString();
				}
			}
		} catch (Exception e) {
			System.out.println("getFileSender Error: " + e);
		}
		
		return senderIP;
	}
	
	public String getFileRequest() {
		return fileRequest;
	}

}