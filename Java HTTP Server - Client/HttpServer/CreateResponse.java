import java.io.*;

public class CreateResponse {
		Client client;
		String cookieId = "";
		HttpServer server;
		
		public CreateResponse(Client client) {
			this.client = client;
		}
		
		public File createResponse(String[] score, int indexHigh, int indexLow, 
					int indexAttempts, int indexWin, int indexGuess, int id) {
			File response = null;
			try {
				
				response = new File("." +"/www/response" + id +".html");
				PrintStream out = new PrintStream(response);
				
				File index = new File("." + "/index.html");
				BufferedReader in = new BufferedReader(new FileReader(index));
				String line = "";
				while ( (line = in.readLine()) != null) {
					if (line.contains("#ChooseDiv")) {
						String line2 = makeHtmlLine("Choose", "Choose Number between ", 
								score[indexLow], " and ", score[indexHigh]);
						out.println(line2);
					} else if (line.contains("AttemptsDiv")) {
						String line2 = makeHtmlLine("Attempts", "Number of Attempts: ", 
								score[indexAttempts]);
						out.println(line2);
					} else if (line.contains("GuessDiv")) {
						String line2 = makeHtmlLine("Guess", "Guess: ", 
								score[indexGuess]);
						out.println(line2);
					} else if (line.contains("#win") & score[indexWin].equalsIgnoreCase("win")) {
						String line2 = makeHtmlLine("Win", "You Win! ", "Attempts: ", String.valueOf(client.previousAttempts),
								" Total Wins: ", String.valueOf(client.totalWins));
						out.println(line2);
			
					} else if (line.contains("#statistics") & score[indexWin].equalsIgnoreCase("win")) {
						int counter = 1;
						for (int i: client.statistics) {
							String line2 = makeHtmlLine("Guess" + String.valueOf(counter),  "Guess: ", 
								String.valueOf(counter), " Attempts: ", 
									String.valueOf(i));
							out.println(line2);
							counter++;							
						}						
					} else {
						if (! (line.contains("#win") ) & ! (line.contains("#statistics")) ) {
							out.println(line);
						}
					}
				}
				in.close();
				out.close();
			} catch (Exception e) {
				System.out.println("Error in createResponse: " + e);
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