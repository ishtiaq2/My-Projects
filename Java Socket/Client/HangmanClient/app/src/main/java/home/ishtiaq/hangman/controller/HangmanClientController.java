package home.ishtiaq.hangman.controller;

/**
 * Created by ishtiaq on 12/14/2017.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import home.ishtiaq.hangman.network.HangmanClientServerConnection;
import home.ishtiaq.hangman.support.ShowInputFromServer;
/**
 * This handles all the tasks that the user want to perform. Each task is performed
 * into it's own separate thread, using Executerservice.
 */

public class HangmanClientController {

    private final ExecutorService tasks = Executors.newFixedThreadPool(4);
    HangmanClientServerConnection serverConnection;
    ShowInputFromServer output;

    public HangmanClientController(ShowInputFromServer output) {
        this.output = output;
        serverConnection = new HangmanClientServerConnection(output);
    }

    public void connect(String cmd, String host, String port) {
        tasks.execute( new Thread(new ConnectionTask(cmd, host, port)) );
    }

    /**
     * this task is responsible to send the user information entered in the form [user:nickname]
     */

    public void sendUserInfo(String cmd, String userName) {
        tasks.execute( new Thread(new SendUserInfoTask(cmd, userName)) );
    }

    /**
     * This task is used to start playing the game and then keep continue playing,
     * until the end or the user terminate
     */

    public void play(String cmd, String game) {
        tasks.execute( new Thread(new SendPlayHangmanTask(cmd, game)) );
    }

    /**
     * disconnect from the server
     */

    public void disconnect(String cmd) {
        tasks.execute( new Thread(new DisconnectTask(cmd)) );
    }

    /**
     * The following are the threads that are responsible to handle
     * the above task.
     */

    private class ConnectionTask implements Runnable {

        String cmd = "";
        String host = "";
        String port = "";

        public ConnectionTask(String cmd, String host, String port) {
            this.cmd = cmd;
            this.host = host;
            this.port = port;
        }
        public void run() {
            try {
                serverConnection.connectionTask(cmd, host, port);
            } catch (Exception e) {
                output.show("Connection Task error: " + e);
            }
        }
    }

    private class SendUserInfoTask implements Runnable {
        String cmd = "";
        String userName = "";

        public SendUserInfoTask(String cmd, String userName) {
            this.cmd = cmd;
            this.userName = userName;
        }

        public void run() {
            try {
                //output.show("User Info Task executed");
                serverConnection.sendUserInfoTask(cmd, userName);
            } catch (Exception e) {

                output.show("Error in senduserinfo task");
            }
        }
    }

    private class SendPlayHangmanTask implements Runnable {
        String cmd = "";
        String game = "";

        public SendPlayHangmanTask(String cmd, String game) {
            this.cmd = cmd;
            this.game = game;
        }

        public void run() {
            try {
                //output.show("PlayHangman Task executed");
                serverConnection.sendPlayHangmanTask(cmd, game);
            } catch (Exception e) {
                output.show("Error in sendPlayHangman");
            }
        }
    }

    private class DisconnectTask implements Runnable {
        String cmd = "";

        public DisconnectTask(String cmd) {
            this.cmd = cmd;

        }

        public void run() {
            try {
                //output.show("disConnect Task executed");
                serverConnection.disconnect(cmd);
            } catch (Exception e) {
                output.show("disConnect Task error");
            }
        }
    }

}
