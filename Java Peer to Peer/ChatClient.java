import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient implements Runnable {
	
	private boolean receivingCmds = false;
	private Scanner consoleInput = new Scanner(System.in);;
	private ClientController controller;
	public volatile boolean serverExtension = false;
	public volatile boolean clientExtension = false;
	
	public void start() {
        if (receivingCmds) {
            return;
        }
        receivingCmds = true;
		ConsoleOutput output = new ConsoleOutput();
		controller = new ClientController(output);
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		System.out.println("Enter [connect ip port] to connect");
		System.out.println("Enter [quit] to exit");
		
		while (receivingCmds) {
			try {
				MessageType msgType = new MessageType(readNextLine());
				switch (msgType.getCmd()) {
					
					case "CONNECT":
						controller.connect(msgType.getParam(0), msgType.getParam(1));						
						break;
						
					case "USER":
						controller.sendUsername(msgType.getParam(0));
						break;
						
					case "UNICAST":
						controller.sendUnicastMessage(msgType.getParam(0), msgType.getEnteredUnicastLine());
						break;
					
					case "SYN":
						controller.sendFileHandlingRequest(msgType.getCmd(), msgType.getParam(0),
								msgType.getParam(1), msgType.getParam(2) );
						break;
					
					case "ACK":
						controller.sendFileHandlingRequest(msgType.getCmd(), msgType.getParam(0),
								msgType.getParam(1), msgType.getParam(2) );
						break;
						
					case "OPENSERVERSOCKET":
						handleServerSocket(msgType.getParam(0)); //port
						break;
						
					case "OPENCLIENTSOCKET":
						handleClientSocket(msgType.getParam(0), msgType.getParam(1));
						break;
						
					case "QUIT":
						receivingCmds = false;
						controller.disconnect();
						break;
						
					default:
						controller.sendEnteredLine(msgType.getEnteredLine());
				}
				
			} catch (Exception e) {
				System.out.println("Error in Chat Client run: " + e);
			}
		}
		
	}
	
	public static void main(String[] args) {
		ChatClient client = new ChatClient();
		client.start();
	}
	
	private class MessageType {
		
		String cmd = "???";
		String[] params;
		private int cmdIndex = 0;
		private String enteredLine = "????";
		private String enteredUnicastLine = " ";
		
		public MessageType(String line) {
			if (line == null || line.equalsIgnoreCase("")) {
				return;
			}
			enteredLine = line;
			parseLine(enteredLine);
		}
		
		public void parseLine(String line) {
			String[] tokens = line.split(" ");
			cmd = tokens[cmdIndex].toUpperCase();
			extractParams(tokens);
		}
		
		public void extractParams(String[] tokens) {
			params = new String[tokens.length -1];
			for (int i = 0; i < params.length ; i++) {
				params[i] = tokens[i+1];
			}
			if (cmd.equalsIgnoreCase("unicast")) {
				extractUnicastMessage(params);
			}
		}
		
		public void extractUnicastMessage(String[] paras) {
			for (int i = 1; i < paras.length; i++){
				enteredUnicastLine += paras[i] + " ";
			}
		}
		
		public String getCmd() {
			return cmd;
		}
		
		public String getParam(int index) {
			return params[index];
		}
		
		public String getEnteredLine() {
			return enteredLine;
		}
		
		public String getEnteredUnicastLine() {
			return enteredUnicastLine;
		}
	}
	
	 private String readNextLine() {
		//System.out.print("> ");
        return consoleInput.nextLine();
    }

    private class ConsoleOutput implements OutputHandler {
        @Override
        public void handleMsg(String msg) {
			
			System.out.println(msg);

			//System.out.print("> ");
        }
	}
	
	public void handleServerSocket(String port) {
		System.out.println(serverExtension);
		try {
			if (!serverExtension) {
				ServerExtension se = new ServerExtension(this, port);
				Thread seThread = new Thread(se);
				seThread.start();
				serverExtension = true;
			} else {
				System.out.println("Wait for file upload...");
			}
		} catch (Exception e) {
			System.out.println("handleServerSocket error: " + e);
		}
	}
	
	public void handleClientSocket(String ip, String port) {
		try {
			if (!clientExtension) {
				ClientExtension ce = new ClientExtension(this, ip, port);
				Thread ceThread = new Thread(ce);
				ceThread.start();
				clientExtension = true;
			} else {
				System.out.println("Wait for file download...");
			}
		} catch (Exception e) {
			System.out.println("handleClientSocket error: " + e);
		}
	}
	
}
