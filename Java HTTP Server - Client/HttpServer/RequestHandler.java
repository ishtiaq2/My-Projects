import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler implements Runnable {
	HttpServer server;
	Socket socket;
	BufferedReader in;
	private boolean newclient = true;
	public RequestHandler(HttpServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}
	
	public void run() {
		Client client = null;
		String guessLine = "";
		String[] score = new String[5];
		int indexHigh = 1;
		int indexLow = 0;
		int indexAttempts = 2;
		int indexWin = 3;
		int indexGuess = 4;
		try {
			in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			String requestLine = in.readLine();
			
			if (requestLine.contains("favicon")) {
				return;
			}
			if (requestLine.contains("guess")) {
				guessLine = requestLine;			///////////user guess
			}
			System.out.println(requestLine);
			StringTokenizer tokens = new StringTokenizer(requestLine," ?");	
			tokens.nextToken();
			String requestedDocument = tokens.nextToken();
			String cookie = "";
			String str = "";
			while ( (str = in.readLine()) != null && str.length() > 0 ) {
				if (str.contains("Cookie")) {
					System.out.println(str);
					newclient = false;
					cookie = str;				////////user id
				}
				System.out.println(str);
			}
			
			if (newclient) {
				client = server.createNewClient();
				score[indexHigh] = String.valueOf(client.getToohigh());
				score[indexLow] = String.valueOf(client.getToolow());
				score[indexAttempts] = String.valueOf(client.getAttempts());
				score[indexWin] = "0";
				score[indexGuess] = "No Guess";
			} else {
				int clientId = getClientId(cookie);
				client = server.getClient(clientId); //based on cookieId
				if (guessLine.length() > 0 ) {
					client.setGuess(readGuessFromRequestLine(guessLine));
				}
				NumberComparer numComparer = new NumberComparer(client);
				score = numComparer.compare(indexHigh, indexLow, indexAttempts, indexWin, indexGuess);
			}
			
			CreateResponse response = new CreateResponse(client);
			File file = response.createResponse(score, indexHigh, indexLow, indexAttempts, indexWin, indexGuess, client.getId());
			socket.shutdownInput();
			
			System.out.println();
			sendResponse(file, client.getId(), "OK");
			socket.shutdownOutput();
			socket.close();
		} catch (Exception e) {
			File f = new File("." + "/error.html");
			sendResponse(f, 0, "Error");
			System.out.println("Error in RequestHandler run: " + e);
			try { 
				socket.close();
			} catch (Exception ee) {
			}
		}
	}
	
	public int readGuessFromRequestLine(String requestLine) {
		String guess = requestLine.substring(requestLine.indexOf("guess"), requestLine.indexOf("HTTP/1.") - 1);
		String gues = guess.trim();
		String[] token = gues.split("=");
		return Integer.parseInt(token[1]);
	}
	
	public int getClientId(String cookie) {
		StringTokenizer cookieTokens = new StringTokenizer(cookie, "=");
		cookieTokens.nextToken();
		int cookieId = Integer.parseInt(cookieTokens.nextToken());
		return cookieId;
	}
	
	public void sendResponse(File file, int id, String responseType) {
		try {
			PrintStream response = new PrintStream(socket.getOutputStream());
			response.println("HTTP/1.0 200 OK");
			response.println("Server : HttpServer");
			
			//if(requestedDocument.indexOf(".html") != -1)
			response.println("Content-Type: text/html");
			//response.println("Set-Cookie: clientId=1; expires=Wednesday,31-Dec-2017 21:00:00 GMT");
			if (responseType.equalsIgnoreCase("OK")) {
				response.println("Set-Cookie: clientId=" + id);
				response.println();
			} else if (responseType.equalsIgnoreCase("error")){
				response.println("Set-Cookie: clientId=0; expires=Wednesday,31-Dec-2017 21:00:00 GMT");
				response.println();
			}
			
			FileInputStream infil = new FileInputStream(file);
			byte[] b = new byte[24];
			while( infil.available() > 0){
				response.write(b,0,infil.read(b));
			}
		} catch (Exception e) {
			System.out.println("Error in sendResponse: " + e);
		}
	}
	
	private class NumberComparer {
		String[] result = new String[5];
		Client client;
		public NumberComparer(Client client) {
			this.client = client;
		}
		
		public String[] compare(int indexHigh, int indexLow, int indexAttempts, int indexWin, int indexGuess) {
			System.out.println("R number : " + client.getrNumber());
			if (client.getGuess() > client.getrNumber() ) {
				if (client.getGuess() - 1 != client.getrNumber()) {
					client.setToohigh(client.getGuess() -1);
				} else {
					client.setToohigh(client.getGuess());
				}
				if ( client.getToolow() + 1 != client.getrNumber() ) {
					client.setToolow(client.getToolow() + 1);
				} else {
					client.setToolow(client.getToolow());
				}
				client.setAttempts(client.getAttempts() + 1);
				result[indexHigh] = String.valueOf(client.getToohigh());
				result[indexLow] = String.valueOf(client.getToolow());
				result[indexAttempts] = String.valueOf(client.getAttempts());
				result[indexWin] = "running";
				result[indexGuess] = "Too High";
			} else if (client.getGuess() < client.getrNumber() ) {
				if (client.getGuess() + 1 != client.getrNumber()) {
					client.setToolow(client.getGuess() + 1);
				} else {
					client.setToolow(client.getGuess());
				}
				if (client.getToohigh() - 1 != client.getrNumber() ) {
					client.setToohigh(client.getToohigh() - 1);
				}
				client.setAttempts(client.getAttempts() + 1);
				result[indexLow] = String.valueOf(client.getToolow());
				result[indexHigh] = String.valueOf(client.getToohigh());
				result[indexAttempts] = String.valueOf(client.getAttempts());
				result[indexWin] = "running";
				result[indexGuess] = "Too Low";
			} else if (client.getGuess() == client.getrNumber() ) {
				System.out.println("You win!");
				client.previousAttempts = client.getAttempts();
				client.totalWins += 1;
				client.reset();
				result[indexLow] = String.valueOf(client.getToolow());
				result[indexHigh] = String.valueOf(client.getToohigh());
				result[indexAttempts] = String.valueOf(client.getAttempts());
				result[indexWin] = "win";
				result[indexGuess] = "Correct!";
				client.statistics.add(client.previousAttempts);
			}
			return result;
		}
	}
	
}