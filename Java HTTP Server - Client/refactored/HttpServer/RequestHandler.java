import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler implements Runnable {
	HttpServer server;
	Client client = null;
	Socket socket;
	BufferedReader in;
	File file;
	//String responseType = "";
	private boolean newclient = true;
	public RequestHandler(HttpServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}
	
	public void run() {
		//Client client = null;
		String guessLine = "";
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
			} else {
				int clientId = getClientId(cookie);
				client = server.getClient(clientId); //based on cookieId
				if (guessLine.length() > 0 ) {
					client.guess = readGuessFromRequestLine(guessLine);
				}
				setClientValues(client);
			}
					
			file = createResponse();
			socket.shutdownInput();
			
			System.out.println();
			sendResponse(file, client.clientId, "OK");
			socket.shutdownOutput();
			socket.close();
		} catch (Exception e) {
			File f = new File("." + "/error.html");
			sendResponse(f, 0, "Error");
			//System.out.println("Error in RequestHandler run: " + e);
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
	
	public void sendResponse(File file, int id, String resType) {
		try {
			PrintStream response = new PrintStream(socket.getOutputStream());
			response.println("HTTP/1.0 200 OK");
			response.println("Server: HttpServer");
			response.println("Connection: close");
			//if(requestedDocument.indexOf(".html") != -1)
			response.println("Content-Type: text/html");
			//response.println("Set-Cookie: clientId=1; expires=Wednesday,31-Dec-2017 21:00:00 GMT");
			if (resType.equalsIgnoreCase("OK")) {
				response.println("Set-Cookie: clientId=" + id);
				response.println();
			} else if (resType.equalsIgnoreCase("error")){
				response.println("Set-Cookie: clientId=0; expires=Wednesday,31-Dec-2017 21:00:00 GMT");
				response.println();
			}
			
			FileInputStream infil = new FileInputStream(file);
			byte[] b = new byte[1024];
			while( infil.available() > 0){
				response.write(b,0,infil.read(b));
			}
		} catch (Exception e) {
			System.out.println("Error in sendResponse: " + e);
		}
	}
	
	public void setClientValues(Client client) {
		
		System.out.println("Random number: " + client.rnumber);
		
		if (client.guess > client.rnumber) {
			if (client.guess - 1 != client.rnumber) {
				client.toohigh = client.guess - 1;
			} else {
				client.toohigh = client.guess;
			}
			if ( client.toolow + 1 != client.rnumber) {
				client.toolow = client.toolow + 1;
			} else {
				client.toolow = client.toolow;
			}
			client.attempts = client.attempts + 1;
			client.win = "running";
			client.highlow = "Too High";
			
		} else if (client.guess < client.rnumber ) {
			if (client.guess + 1 != client.rnumber) {
				client.toolow = client.guess + 1;
			} else {
				client.toolow = client.guess;
			}
			if (client.toohigh - 1 != client.rnumber ) {
				client.toohigh = client.toohigh - 1;
			}
			client.attempts = client.attempts + 1;
			client.win = "running";
			client.highlow = "Too Low";
			
		} else if (client.guess == client.rnumber) {
			System.out.println("You win!");
			client.previousAttempts = client.attempts;
			client.totalWins += 1;
			client.reset();
			client.win = "win";
			client.highlow = "Correct!";
			client.statistics.add(client.previousAttempts);
		}
	}
	
	public File createResponse() {
		File response = null;
		try {
			
			response = new File("." +"/www/response" + client.clientId +".html");
			PrintStream out = new PrintStream(response);
				
			File index = new File("." + "/index.html");
			BufferedReader in = new BufferedReader(new FileReader(index));
			String line = "";
			while ( (line = in.readLine()) != null) {
				if (line.contains("#ChooseDiv")) {
					String line2 = makeHtmlLine("Choose", "Choose Number between ", 
							String.valueOf(client.toolow), " and ", String.valueOf(client.toohigh));
					out.println(line2);
				} else if (line.contains("AttemptsDiv")) {
					String line2 = makeHtmlLine( "Attempts", "Number of Attempts: ", 
							String.valueOf(client.attempts) );
					out.println(line2);
				} else if (line.contains("GuessDiv")) {
					String line2 = makeHtmlLine("Guess", "Guess: ", 
						String.valueOf(client.highlow));
					out.println(line2);
				} else if (line.contains("#win") & client.win.equalsIgnoreCase("win")) {
					String line2 = makeHtmlLine("Win", "You Win! ", "Attempts: ", String.valueOf(client.previousAttempts),
							" Total Wins: ", String.valueOf(client.totalWins));
					out.println(line2);
			
				} else if (line.contains("#statistics") & client.win.equalsIgnoreCase("win")) {
					int counter = 1;
					for (int i: client.statistics) {
						String line2 = makeHtmlLine("Guess" + String.valueOf(counter),  "Guess: ", 
							String.valueOf(counter), " Attempts: ", String.valueOf(i));
						out.println(line2);
						counter++;							
					}						
				} else {
					if (! (line.contains("#win") ) & ! (line.contains("#statistics")) ) {
						out.println(line);
					}
				}
			}
			//responseType = "OK";
			in.close();
			out.close();
		} catch (Exception e) {
			System.out.println("Error in createResponse: " + e);
			//responseType = "Error";
			response = new File("." + "/error.html");
		}
		return response;
	}
		
	public String makeHtmlLine(String... paras) {
		String[] par = paras;
		StringBuilder str = new StringBuilder("<div id='" + par[0] + "'>");
		for (int i = 1; i < par.length; i++) {
			str.append(par[i]);
		}
		str.append(" </div>");
		return str.toString();
	}
			
}