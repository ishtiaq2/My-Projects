import java.util.*;

public class Client {
	
	private int clientId = 0;
	private int guess = 0;
	private int toolow = 0;
	private int toohigh = 100;
	private int attempts = 0;
	public int previousAttempts = 0;
	public int totalWins = 0;
	public List<Integer> statistics = new ArrayList<>();
	
	String sendResponse = "index";
	int rnumber = 0;
	HttpServer server;
	
	public Client(HttpServer server, int clientId) {
		this.clientId = clientId;
		this.server = server;
	}
	
	public void setrNumber(int rnumber) {
		this.rnumber = rnumber;
	}
	
	public int getrNumber() {
		return rnumber;
	}
	
	public void setSendResponse() {
		sendResponse = "response";
	}
	
	public String getSendResponse() {
		return sendResponse;
	}
	
	public int getId() {
		return clientId;
	}
	
	public void setGuess(int guess) {
		this.guess = guess;
	}
	
	public int getGuess() {
		return guess;
	}
		
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	
	public int getAttempts() {
		return attempts;
	}

	public void setToolow(int toolow) {
		this.toolow = toolow;
	}
	
	public int getToolow() {
		return toolow;
	}
	
	public void setToohigh(int toohigh) {
		this.toohigh = toohigh;
	}
	
	public int getToohigh() {
		return toohigh;
	}
	
	public void reset() {
		guess = 0;
		toolow = 0;
		toohigh = 100;
		attempts = 0;
		server.setRandomNumber(this);
	}
}