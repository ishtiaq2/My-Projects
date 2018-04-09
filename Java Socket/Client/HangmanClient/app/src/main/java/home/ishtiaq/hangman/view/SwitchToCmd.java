package home.ishtiaq.hangman.view;

import home.ishtiaq.hangman.controller.HangmanClientController;
import home.ishtiaq.hangman.support.ShowInputFromServer;

/**
 * Created by ishtiaq on 12/15/2017.
 */

public class SwitchToCmd {
    HangmanClientController hangmanClientController;
    ShowInputFromServer outputHandler;

    String arg1 = "Enter [connect: ip, port] to connect";
    String arg2 = "Enter [quit] to exit";

    public SwitchToCmd(ShowInputFromServer outputHandler, HangmanClientController hangmanClientController) {
        this.outputHandler = outputHandler;
        this.hangmanClientController = hangmanClientController;
    }

    public void switchToCmd(String cmd) {
        switch(cmd) {

            case "connect":		//connect to server(ip, port) by calling control class method // on success set flag to stop more connection commands
                if (!MainActivity.connected) {
                    hangmanClientController.connect(cmd, LineParser.host, LineParser.port);
                } else {
                    outputHandler.show("Already connected");
                }
                break;

            case "user":
                if (!MainActivity.connected) {
                    outputHandler.show("You are not connected");
                    outputHandler.show(arg1);
                    outputHandler.show(arg2);
                } else {
                    if(!MainActivity.registered) {
                        hangmanClientController.sendUserInfo(cmd, LineParser.userName);
                    } else {
                        outputHandler.show("Already registered");
                    }
                }
                break;

            case "play":
                if ( (!MainActivity.connected) | (!MainActivity.registered) ){
                    outputHandler.show("You are not connected and(or) not registered");
                    outputHandler.show(arg1);
                    //outputHandler.show("Enter [user: name] to register");
                    outputHandler.show(arg2);
                } else {
                    if (LineParser.GAME.equalsIgnoreCase("hangman1") & (!LineParser.playing) ) {
                        outputHandler.show("Wait while loading...");
                        hangmanClientController.play(cmd, LineParser.GAME);
                        LineParser.playing = true;
                       MainActivity.formatInput = "play:";
                    } else {
                        hangmanClientController.play(cmd, LineParser.GAME);
                        MainActivity.formatInput = "play:";
                    }
                }
                break;

            case "quit":
                if (MainActivity.connected) {
                    hangmanClientController.disconnect(cmd);
                    LineParser.running = false;
                    try {
                        Thread.sleep(5000);
                        LineParser.running = false;
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
}
