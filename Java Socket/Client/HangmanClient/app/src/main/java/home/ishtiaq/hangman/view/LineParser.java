package home.ishtiaq.hangman.view;

import home.ishtiaq.hangman.controller.HangmanClientController;
import home.ishtiaq.hangman.support.ShowInputFromServer;

/**
 * Created by ishtiaq on 12/15/2017.
 */

public class LineParser {

    protected static String host = "";
    protected static String port = "";
    protected static String userName = "";
    protected static String GAME = " ";
    protected static boolean running = true;
    protected static boolean playing = false;
    String exception = "";

    HangmanClientController hangmanClientController;
    ShowInputFromServer outputHandler;

    public LineParser(ShowInputFromServer outputHandler, HangmanClientController hangmanClientController) {
        this.outputHandler = outputHandler;
        this.hangmanClientController = hangmanClientController;
    }

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
}
