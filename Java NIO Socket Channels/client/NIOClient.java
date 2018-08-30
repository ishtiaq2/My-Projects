public class NIOClient {
    
    private ServerConnection serverConnection;
    
    public NIOClient() throws Exception {
		HangmanClientView view = new HangmanClientView();
		view.start();
    }
    
    public static void main(String[] args) throws Exception {
        NIOClient client = new NIOClient();
    }
    
}