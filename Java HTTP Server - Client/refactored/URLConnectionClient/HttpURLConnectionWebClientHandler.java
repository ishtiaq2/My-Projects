import java.io.*;
import java.net.*;
import java.util.*;

public class HttpURLConnectionWebClientHandler {
	
	HttpURLConnectionWebClient webClient;
	String httpRequest = "GET / HTTP/1.1";
	String httpHeader = "Host: HttpServer";
	String cookieLine = "";
	HttpURLConnection con;
	
	public HttpURLConnectionWebClientHandler(HttpURLConnectionWebClient webClient) {
		this.webClient = webClient;
	}
	
	public void run() {
		System.out.println("********************************");
		System.out.println(">Round: " + webClient.round);
		try {
			sendGET();
		} catch (Exception e) {
			System.out.println("Erro in run: " + e);
		}
	}
	
	private void sendGET()  {
		try {
			
			//Scanner sc = new Scanner(System.in);
			
			if (!webClient.sessionStart) {
				
				URL obj = new URL("http://localhost");
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Host", "HttpServer");
				//int responseCode = con.getResponseCode();
				//if (responseCode == HttpURLConnection.HTTP_OK) { // success
			} else if (webClient.sessionStart) {
				//System.out.print(">Enter Guess Number: ");
				//String guess = sc.nextLine();
				System.out.println(">Binary Guess: " + webClient.initialBinaryGuess);
				httpRequest = "http://localhost/" + "?guess=" + webClient.initialBinaryGuess;
				URL obj = new URL(httpRequest);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Host", "HttpServer");
				con.setRequestProperty("Cookie", webClient.cookie);
			}
			
			for (int i = 0; i < 5; i++) {
				String headerName = con.getHeaderFieldKey(i);
				String headerValue = con.getHeaderField(i);
				if ("Set-Cookie".equalsIgnoreCase(headerName)) {
					//System.out.print(">" + headerName + ": ");
					//System.out.println(headerValue);
					webClient.cookie = headerValue;
				}
			}

			
			BufferedReader in = new BufferedReader(new InputStreamReader(
			con.getInputStream()));
			String str;
			StringBuffer response = new StringBuffer();

			while ( (str = in.readLine()) != null) {
				if (str.contains("Number of Attempts")) {
					String s = parseLine(str);
					System.out.println(s);
				} else if (str.contains("Guess: ") & ! str.contains("Attempts")) {
					String s = parseLine(str);
					setBinaryGuess(s);
					System.out.println(s);
				} else if (str.contains("Choose Num")) {
					String s = parseLine(str);
					//System.out.println(s);
					System.out.println("Choose Number between " + webClient.lowerBound +
										" and " + webClient.upperBound);
				} else if (str.contains("Guess")){
					String s = parseLine(str);
					//System.out.println(s);
				} else if (str.contains("Win")) {
					System.out.println();
					String s = parseLine(str);
					System.out.println(s);
				} else {
						//don not print
				}
			}
			in.close();
			webClient.sessionStart = true;
		} catch (Exception e) {
			System.out.println("Problem here: " + e);
		} finally {
			try {
				con.disconnect();
			} catch (Exception ee) {
				System.out.println("Problem here2: " + ee);
			}			
			con = null;
		}
	}
	
	public String parseLine(String ln) {
		String line = ln.substring(ln.indexOf("'>") + 1, ln.indexOf("</") -1 );
		return line;
	}
	
	public void setBinaryGuess(String s) {
		String[] guess = s.split(":");
		String result = guess[1].trim();
		if (result.contains("Too High")) {
			webClient.numberOfAttempts = webClient.numberOfAttempts + 1;
			webClient.upperBound = webClient.initialBinaryGuess;
			setInitialBinaryGuess();
		} else if (result.contains("Too Low")) {
			webClient.numberOfAttempts = webClient.numberOfAttempts + 1;
			webClient.lowerBound = webClient.initialBinaryGuess;
			setInitialBinaryGuess();
		} else if (result.contains("Correct")) {
			webClient.numberOfAttempts = webClient.numberOfAttempts + 1;
			webClient.lowerBound = 0;
			webClient.upperBound = 100;
			setInitialBinaryGuess();
			webClient.numberOfTimesPlayed = webClient.numberOfTimesPlayed + 1;
			webClient.average.add(webClient.numberOfAttempts);
			webClient.numberOfAttempts = 0;
			webClient.round++;
			
			if (webClient.round > 99) {
				showAverage();
			}
		} else {
			//do nothing
		}
		
	}
	
	public void setInitialBinaryGuess() {
		webClient.initialBinaryGuess = (webClient.lowerBound + webClient.upperBound)/2;
	}
	
	public void showAverage() {
		System.out.println();
		System.out.println(">/********* Final Result: Average *************/");
		
		int attempts = 0;
		for (int counter: webClient.average) {
			attempts = attempts + counter;
			//System.out.println("Entry of Average: " + counter);
			
		}
		System.out.println(">Sum of Attempts: " + attempts);
		System.out.println(">Num of Times Played : " + webClient.numberOfTimesPlayed);
		double avg =  (double)attempts / (double)webClient.numberOfTimesPlayed;
		
		System.out.println(">Average Guess: " + avg);
		
		System.out.println(">/********** End of Game ******************/");
		System.out.println();
	}
}
		