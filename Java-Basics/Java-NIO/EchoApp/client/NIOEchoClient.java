public class NIOEchoClient {
    
    private ServerConnection serverConnection;
    
    public NIOEchoClient() throws Exception {
        serverConnection = new ServerConnection();
        serverConnection.serve();
    }
    
    public static void main(String[] args) throws Exception {
        NIOEchoClient client = new NIOEchoClient();
    }
    
}