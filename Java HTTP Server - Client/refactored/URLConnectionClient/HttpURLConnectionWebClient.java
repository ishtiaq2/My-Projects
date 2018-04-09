import java.io.*;
import java.net.*;
import java.util.*;

public class HttpURLConnectionWebClient {
	public volatile String cookie = "";
	public volatile boolean newRequest = true;
	public volatile boolean sessionStart = false;
	public volatile int numberOfTimesPlayed = 0;
	public volatile int numberOfAttempts = 0;
	public volatile int lowerBound = 0;
	public volatile int upperBound = 100;
	public volatile int initialBinaryGuess = 50;
	public volatile List<Integer> average = new ArrayList<>();
	public volatile int round = 0;	
	public static void main(String[] args ) throws Exception {
		
		HttpURLConnectionWebClient c = new HttpURLConnectionWebClient();
		c.serve();
	}
		
	public void serve() throws Exception {
		
		while (true) {
			if (newRequest()) {
				HttpURLConnectionWebClientHandler webClientHandler = new HttpURLConnectionWebClientHandler(this);
				webClientHandler.run();
			}
			//setNewRequest(false);
		}
	}
	
	public boolean newRequest() {
		
		if (round < 100 && round >= 0) {
			return true;
		} else { 
			return false;
		}
	}
	
	public void setNewRequest(boolean b) {
		newRequest = b;
	}
}