
public class Peer {
	
	int sno = 0;
	String sender = "";
	String receiver = "";
	String file = "";
	boolean SYN = true;
	boolean ACK = false;
	boolean ready = false;
	String port = "0";
	String ip = "0";
	
	
	public Peer(int sno, String sender, String receiver, String file, String ip) {
		this.sno = sno;
		this.sender = sender;
		this.receiver = receiver;		
		this.file = file;
		this.ip = ip;
	}
	
	public String toString() {
		return sno + " " + sender + " " + receiver + " " + file +
					 " " + SYN + " " + ACK + " IP:" + ip + ":" + port + " ready: " + ready;
	}
	
	public void setACK(boolean ACK) {
		this.ACK = ACK;
	}
	
	public boolean getACK() {
		return ACK;
	}
	
	public boolean getSYN() {
		return SYN;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public boolean getReady() {
		return ready;
	}
	
	public int getSno() {
		return sno;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public String getFile() {
		return file;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getPort() {
		return port;
	}
	public void setIP(String ip) {
		this.ip = ip;
	}
	public String getIP() {
		return ip;
	}
	
}