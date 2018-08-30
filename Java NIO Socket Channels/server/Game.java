import java.nio.channels.SelectionKey;
import java.io.*;
import java.nio.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.*;
import java.util.*;

public class Game {

	ClientHandler clientHandler;
	SelectionKey key;
	boolean connected = true;	
	String userName = "";
	String cmd = "";
	String game = "";
	
	private boolean playing = false;
	private boolean sendAtStart = true;
	public String word = "";
	private String userGuess = "";
	private ChooseWord chooseWord;
	private PlayGame playGame;
	private int totalScore = 0;
		
	public Game(ClientHandler clientHandler, SelectionKey key) {
		this.clientHandler = clientHandler;
		this.key = key;		
	}
	 
	public String runIt(String msg) {
		String response = "";
		
		try {
			parseLine(msg);
			switch(cmd) {
				case "user":
					System.out.println(userName +" is registered now");
					response = "Welcome " + userName + "\n" + 
								"Enter [play:hangman] to play game" +"\n" ;
					break;
						
				case "play":
					if (!playing) {
						System.out.println(userName +" want to play " +game);
						chooseWord = new ChooseWord();
						chooseWord.start();
						try {
							Thread.sleep(500);
						} catch (Exception e) {
						}
						System.out.println("Word : " + word);
						playGame = new PlayGame(word);
						response = playGame.processWord(word);
						playing = true;
					} else {
						response = playGame.processWord(userGuess);
					}
					break;
					
				case "reset": 
					playing = false;
					System.out.println("Game reset");
					break;
				case "quit":
					connected = false;
					closeConnection("User Disconnedted");
					break;
				default:
					System.out.println("Invalid command");
					break;
			}
			
		} catch (Exception e) {
			closeConnection("Connection Lost");
		}
		return response;
	}
	
	/**
	 * close the connection if an exception occure, or the user want to quit.
	 */
	 
	public void closeConnection(String s) {
		System.out.println(s);
		connected = false;
		try {
			clientHandler.server.removeClient(key);
		} catch (Exception ee) {
		}
	}
	
	/**
	 * analyse the user data
	 */
	 
	public String parseLine(String line) {
		String[] temp = line.split(":");
		
		if (temp[0].equalsIgnoreCase("user")) {
			cmd = temp[0];
			userName = temp[1];
		} else if (temp[0].equalsIgnoreCase("play")) {
			if (!playing) {
				cmd = temp[0];
				game = temp[1];
			} else {
				cmd = temp[0];
				userGuess = temp[1];
			}
			
		} else if(temp[0].equalsIgnoreCase("quit")) {
			System.out.println(temp[0]);
			cmd = temp[0];
		} else if (temp[0].equalsIgnoreCase("reset")) {
			cmd = temp[0];
		} else {
			cmd = "invalid command";
		}
		return cmd;
	}
	
	/**
	 * This is used to randomly choose a word from a file, if the client wan to play.
	 */
	 
	private class ChooseWord extends Thread {
				
		public void run() {
			int r = 0;
			int lineCounter = 0;
			String[] fileArray;
			try {
				Scanner file = new Scanner(new BufferedReader(new FileReader("words.txt")));
				file.useDelimiter("\r\n");
				while(file.hasNext()) {
					lineCounter++;
					file.next();
				}
				file.close();
				r = 1 + (int)(Math.random() * lineCounter);
				
				Scanner guess = new Scanner(new BufferedReader(new FileReader("words.txt")));
				
				int temp = 0;
				String tem = "";
				while(guess.hasNext()) {
					temp++;
					if ( temp == r ) {
						word = guess.nextLine();
					} else {
						tem = guess.nextLine();
					}
				}
				guess.close();							
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * When the user is interested to play, the controle reamin here
	 */
	 
	private class PlayGame {
		String actualWord = "";
		
		int attempts = 0;
		int tempScore = totalScore;
		boolean sendFirst = true;
		private boolean attemptStatus = false;
		char[] ch;
		char c;
		
		public PlayGame(String actualWord) {
			this.actualWord = actualWord;
			ch = new char[actualWord.length()];
			attempts = actualWord.length();
		}
		
		public String processWord(String userGuess) {
			String response = "";
			if (sendFirst) {
				attempts = actualWord.length();			
				for (int i=0; i< attempts; i++) {
					ch[i] = '-';
				}
				response = sendGuess(ch);
				sendFirst = false;
			} else if (attempts > 0) {
				if (actualWord.indexOf(userGuess) != -1) {
					c = userGuess.charAt(0);
					for (int i = 0; i< actualWord.length(); i++) {	
						char t = actualWord.charAt(i);
						if (t == c) {
							System.out.println("Successfull Guess: " + c);
							ch[i] = c;
						}
					}
					if ( (actualWord.equalsIgnoreCase(userGuess)) || ( String.valueOf(ch).equalsIgnoreCase(actualWord) ) ) {
						System.out.println("User wins ...........");
						
						for (int i = 0; i< actualWord.length(); i++) {
							ch[i] = actualWord.charAt(i);
						}
						totalScore++;
					}					
					attemptStatus = true;
					response = sendGuess(ch);
				} else {
					attempts = attempts - 1;
					attemptStatus = false;
					response = sendGuess(ch);
				}
			} else {
				char[] t= {'l', 'o', 's', 't'};
				response = sendGuess(t);
			}
			return response;
		}
		
		public String sendGuess(char[] s) {
			String response = "";
			String str = String.valueOf(s);
			if ( (totalScore == tempScore) && (attempts > 0)) {
				response = "*****************************" + "\n" +
					"Game On..."  + "\n";
				if ( (attemptStatus) && (!sendFirst) ) { //start: !sendfirst
					response += "Successfull Attempt!\n";
				} else if ((!attemptStatus) && (!sendFirst)){
					response += "Failed Attempt\n";
				}
				response += "Remaining Failed Attempts: " + attempts + "\n" +
					"Total Score: " + totalScore  + "\n" +
					"Word offered: " + str  + "\n" +
					"Enter [play:a or play:word] where 'a' or 'word' is your choice of chars" + "\n";
				} else if (totalScore > tempScore) {
					response = "*************************************" + "\n" +
					str + "\n" +
					"You Win!" + "\n" +
					"Reamining Failed Attempts: " + attempts  + "\n" +
					 "Total Score: " + totalScore + "\n" +
					"Reset to play again" + "\n";
					resetGame("");
					sendFirst = true;
				} else if (attempts == 0) {
					totalScore = totalScore - 1;
					response = "*************************************"  + "\n" +
					"You lost!" + "\n" +
					"Reamining Failed Attempts: " + attempts + "\n" +
					"Total Score: " + totalScore + "\n" +
					"Reset to play again" + "\n";
					resetGame("");
					sendFirst = true;
				}
				return response;
		}
		
		public void resetGame(String reset) {
			playing = false;
		}
	}
}