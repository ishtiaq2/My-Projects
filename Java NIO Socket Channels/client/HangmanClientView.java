import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This is used for I/O (View) purpose. The user is asked to enter a line, the 
 * line is analysed to get the command and take action based on the command.
 * This is also used to display the information send by the server.
 */

public class HangmanClientView implements Runnable {
	
	private Scanner consoleInput;
	private String cmd = "";
	private String host = "";
	private String port = "";
	private String userName = "";
	private String GAME = " ";
	private boolean running = true;
	
	private boolean connected = false;
	private boolean registered = false;
	private boolean playing = false;
		
	// HangmanClientController hangmanClientController;
	ServerConnection serverConnection;
	OutputHandler outputHandler;
	
	/**
	 * Create object of HangmanClientController and OutputHandler
	 */
	
	public HangmanClientView() throws Exception {
		outputHandler = new OutputHandler();
		serverConnection = new ServerConnection(this);
        new Thread(serverConnection).start();
	}
	
	public void show(String s) {
		if (s.indexOf("Connected") != -1) {
				connected = true;
				System.out.println(s);
		} else if (s.indexOf("Reset") != -1) {
			System.out.print(s);
			System.out.println("Enter [play:hangman] to play");
			playing = false;
		} else {
			System.out.println(s);
		}
	}
		
	public void start() {
		consoleInput = new Scanner(System.in);
		try {
			new Thread(this).start();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Get input from the user and anaylyse it to get the command
	 * that shows the user intention and action.
	 */
	public void run() {
		String arg2 = "Enter [quit] to exit";
		outputHandler.show(arg2);
		try {
			while(running) {
				cmd = parseLine(consoleInput.nextLine());		
				switch(cmd) {
					case "connect": 
						if (!connected) {
							outputHandler.show("Already connected");
						} else {
							outputHandler.show("Already connected");
						}
						break;
				
					case "user":
						if (!connected) {
							outputHandler.show("You are not connected");
							outputHandler.show(arg2);
						} else {
							if(!registered) {
								serverConnection.send(cmd, userName);
								registered = true;
							} else {
							outputHandler.show("Already registered");
							}
						}
						break;
				
					case "play":
						if ( (!connected) || (!registered) ){
							outputHandler.show("You are not connected and(or) not registered");
							outputHandler.show("Enter [user: name] to register");
							outputHandler.show(arg2);
					
						} else {
							serverConnection.send(cmd, GAME);
						}
						break;
		
					case "reset":
						if (!playing) {
							serverConnection.send(cmd, GAME);
						} else {
							System.out.println("No game to reset");
						}
						break;
					
					case "quit":
						if (connected) {
							running = false;
							try {
								Thread.sleep(500);
								running = false;
							} catch (Exception e){}
								System.exit(0);
						} else {
							System.exit(0);
						}
						break;
					
					default:
						outputHandler.show("Invalid command/Invalid command format");
				}
			}
		} catch(Exception e) {
		}
	}
	
	/**
	 * The input from the user is analyzed here, that returns the command.
	 */
	 
	public String parseLine(String line) {
		String command = "";
		if (line.indexOf("connect") != -1) {
			try {
				String[] comand = line.split(":");
				command = comand[0];
				String[] connectParas = comand[1].split(",");
				host = connectParas[0].replaceAll("\\s+","");
				port = connectParas[1].replaceAll("\\s+","");	
			} catch (Exception e) {
				outputHandler.show("Line format exception");
				command = "Invalid command";
			}
		} else if (line.indexOf("user") != -1) {
			try {
				String[] comand = line.split(":");
				command = comand[0].replaceAll("\\s+","");
				userName = comand[1].replaceAll("\\s+","");
				
			} catch (Exception e) {
				outputHandler.show("Line format exception");
				command = "Invalid command";
			}
		} else if (line.indexOf("play") != -1) {
			try {
				String[] comand = line.split(":");
				command = comand[0].replaceAll("\\s+","");
				GAME = comand[1];
				if ( (!GAME.equalsIgnoreCase("hangman")) && (!playing) ){
					outputHandler.show("Enter [play:hangman] to start playing hangman");
					command = "invalid command";
				} else {
					playing = true;
				}
				
			} catch (Exception e) {
				outputHandler.show("Line format exception:" + e);
			}			
		} else if(line.indexOf("reset") != -1){
			try {
				String[] comand = line.split(":");
				command = comand[0].replaceAll("\\s+","");
				GAME = comand[1];
				playing = false;
				
			} catch (Exception e) {
				outputHandler.show("Line format exception:" + e);
			}
		} else if (line.indexOf("quit") != -1) {			
			try {
				command = "quit";		
				
			} catch (Exception e) {
				outputHandler.show("Line format exception:" + e);
			}
		} else {
			command = "Invalid command";
		}
		return command;		
	}
	
	/**
	 * This inner class (that implements an interface) is used to display 
	 * the information sent by the server.
	 * The purpose is to display the information directly from the network layer
	 * instead of sending it controller and then to the UI.
	 */
	 
	private class OutputHandler implements ShowInputFromServer {
		
		@Override
		public void show(String s) {
			if (s.indexOf("Connected") != -1) {
				connected = true;
			} else if (s.indexOf("Reset") != -1) {
				System.out.println("Enter [play:hangman] to play");
				playing = false;
			} else {
				System.out.println(s);
			}
		}
	}
}