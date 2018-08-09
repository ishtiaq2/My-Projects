import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 *
 * @author ishtiaq
 */
public class ClientHandler {
    
    private NIOEchoServer server;
    private SocketChannel clientChannel;
    private ByteBuffer msgFromClient = ByteBuffer.allocate(1024);
    private int clientId;
    String echoMsg;
    
    public ClientHandler(NIOEchoServer server, SocketChannel clientChannel, int clientId) {
        this.server = server;
        this.clientChannel = clientChannel;
        this.clientId = clientId;
    }
    
    void sendMsg() throws IOException {
        ByteBuffer msgToClient = ByteBuffer.wrap(echoMsg.getBytes());
        clientChannel.write(msgToClient);
        if (msgToClient.hasRemaining()) {
            System.out.println("Could not send message");
        }
    }
    
    void recvMsg(SelectionKey key) throws Exception {
        msgFromClient.clear();
        int numOfReadBytes;
        numOfReadBytes = clientChannel.read(msgFromClient);
        if (numOfReadBytes == -1) {
            throw new IOException("Client has closed connection.");
        }
        msgFromClient.flip();
        byte[] bytes = new byte[msgFromClient.remaining()];
        msgFromClient.get(bytes);
        String recvdMsg = new String(bytes);
        System.out.println("Client: " + clientId + " - "  +recvdMsg);
        echoMsg = "Echo Id: " + clientId + " - " +recvdMsg; 
        server.registerWriteOperation(key);
        
    }
        
    void disconnectClient() throws IOException {
        clientChannel.close();
        System.out.println("Client Disconnected");
    }
}
