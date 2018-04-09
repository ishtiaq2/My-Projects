import java.util.*;

public class Client {
	
	int clientId = 0;
	//String userAgent = "";		//to prevent session (cookie) hijacking
	//String hostIP = "";
	int guess = 0;
	int toolow = 0;
	int toohigh = 100;
	int attempts = 0;
	int previousAttempts = 0;
	int totalWins = 0;
	String win = "";
	String highlow = "";
	List<Integer> statistics = new ArrayList<>();
	
	String sendResponse = "index";
	int rnumber = 0;
	HttpServer server;
	
	public Client(HttpServer server, int clientId) {
		this.clientId = clientId;
		this.server = server;
	}
	
	public void reset() {
		guess = 0;
		toolow = 0;
		toohigh = 100;
		attempts = 0;
		server.setRandomNumber(this);
	}
}