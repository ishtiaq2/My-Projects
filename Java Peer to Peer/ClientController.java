import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController {

    private final ExecutorService tasks = Executors.newFixedThreadPool(5);
    //ServerConnection serverConnection;
    OutputHandler output;
	ServerConnection serverConnection;
	
    public ClientController(OutputHandler output) {
        this.output = output;
        serverConnection = new ServerConnection(output);
    }

    public void connect(String host, String port) {
        tasks.execute( new Thread(new ConnectionTask(host, port)) );
    }
	
	public void sendUsername(String userName) {
        tasks.execute( new Thread(new SendUsernameTask(userName)) );
    }
	
	public void sendEnteredLine(String line) {
		tasks.execute( new Thread(new SendEnteredLineTask(line)) );
	}
	
	public void sendUnicastMessage(String receiver, String msg) {
		tasks.execute( new Thread(new SendUnicastMessageTask(receiver, msg)) );
	}
	
	public void sendFileHandlingRequest(String cmd, String sno, String receiver, String file) {
		tasks.execute( new Thread(new SendFileHandlingTask(cmd, sno, receiver, file)) );
	}
	
    public void disconnect() {
        tasks.execute( new Thread(new DisconnectTask()) );
    }
	
	private class ConnectionTask implements Runnable {

        String host = "";
        String port = "";

        public ConnectionTask(String host, String port) {
            this.host = host;
            this.port = port;
        }
        public void run() {
            try {
                serverConnection.connect(host, port);
            } catch (Exception e) {
                output.handleMsg("Connection Task error: " + e);
            }
        }
    }
	
	
    private class SendUsernameTask implements Runnable {
        String username = "";

        public SendUsernameTask(String username) {
            this.username = username;
        }

        public void run() {
            try {
                //output.show("User Info Task executed");
                serverConnection.sendUsername(username);
            } catch (Exception e) {
                output.handleMsg("Error in send username task");
            }
        }
    }
	
	
    private class SendEnteredLineTask implements Runnable {
        String enteredLine = "";

        public SendEnteredLineTask(String enteredLine) {
            this.enteredLine = enteredLine;
        }

        public void run() {
            try {
                serverConnection.sendEnteredLine(enteredLine);
            } catch (Exception e) {
                output.handleMsg("Error in sendEnteredLine");
            }
        }
    }
	
	private class SendUnicastMessageTask implements Runnable {
        String receiver = "";
		String msg = "";
		
		public SendUnicastMessageTask(String receiver, String msg) {
            this.receiver = receiver;
			this.msg = msg;
		}

        public void run() {
            try {
                serverConnection.sendUnicastMessage(receiver, msg);
            } catch (Exception e) {
                output.handleMsg("Error in sendUnicastMessage");
            }
        }
    }
	
	private class SendFileHandlingTask implements Runnable {
		String sno = "";
		String cmd = "";
        String receiver = "";
		String file = "";
		
        public SendFileHandlingTask(String cmd, String sno, String receiver, String file) {
			this.cmd = cmd;
			this.sno = sno;
            this.receiver = receiver;
			this.file = file;
		}
        public void run() {
            try {
                serverConnection.sendFileHandlingRequest(cmd, sno, receiver, file);
            } catch (Exception e) {
                output.handleMsg("Error in sendFileRequest");
            }
        }
    }
	
    private class DisconnectTask implements Runnable {
        public void run() {
            try {
                serverConnection.disconnect();
            } catch (Exception e) {
                output.handleMsg("disConnect Task error");
            }
        }
    }
	

}
