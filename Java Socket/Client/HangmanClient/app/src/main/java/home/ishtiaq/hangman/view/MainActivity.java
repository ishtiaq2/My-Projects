package home.ishtiaq.hangman.view;

import home.ishtiaq.hangman.controller.HangmanClientController;
import home.ishtiaq.hangman.support.ShowInputFromServer;
import home.ishtiaq.hangman.R;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    HangmanClientController hangmanClientController;
    OutputHandler outputHandler;
    LineParser parser;
    SwitchToCmd switchToCmd;
    PerformAction asyncTask;

    protected static boolean connected = false;
    protected static boolean registered = false;
    protected static String formatInput = "connect:";
    private String cmd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputHandler = new OutputHandler();
        hangmanClientController = new HangmanClientController(outputHandler);
        parser = new LineParser(outputHandler, hangmanClientController);
        switchToCmd = new SwitchToCmd(outputHandler, hangmanClientController);

    }

    public void catchUserAction(View view) {
        Button btn = (Button) view;
        String action = btn.getText().toString();
        EditText txt = findViewById(R.id.txtConnect);
        String txtStr = txt.getText().toString();
        txt.setFocusable(false);

        if ( txtStr.equals("") | txtStr == null) {
            txtStr = "1";
        }
        String msg = formatInput+txtStr;
        txt.setText("");

        if ( action.equalsIgnoreCase("Disconnect")) {
            new PerformAction().execute("quit"); //cmd = Asyc return     -> Asynret = parser.parseLine;
            txt.setText("Wait while closing..");
        } else {
            new PerformAction().execute(msg);
        }

        txt.setFocusableInTouchMode(true);
    }

    private class PerformAction extends AsyncTask<String, Void, String> {

        @Override
        public String doInBackground(String... param) {
            String result = "";
            try {
                result = parser.parseLine(param[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void onPostExecute(String result) {
            cmd = result;
            switchToCmd.switchToCmd(cmd);
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateGUI(String s) {
        String outPut = s;

        if ( (s.indexOf("Connected") != -1 | s.indexOf("register") != -1) & (!connected) )  {
            connected = true;
            Button btn = findViewById(R.id.btnConnect);
            btn.setText("Register");
            formatInput = "user:";
            outPut = "Enter [name] to register";
            EditText txt = findViewById(R.id.txtStatus);
            txt.append(outPut + "\n");

        } else if ( (s.indexOf("Welcome ") != -1 | s.indexOf("play:hang") != -1) & (!registered) ) {
            registered = true;
            Button btn = findViewById(R.id.btnConnect);
            btn.setText("Play");
            formatInput = "play:hangman";
            outPut = "Click Play to start game";
            EditText txt = findViewById(R.id.txtStatus);
            txt.append(outPut + "\n");

        } else if ( s.indexOf("register") == -1 ) {
            if ( (s.indexOf("lost") != -1) | (s.indexOf("Win") != -1) ) {
                    formatInput = "play:hangman";
                    LineParser.playing = false;
            }
            if (s.indexOf("hangman") != -1) {
                outPut = "Click Play to play again";
            }
             if (s.indexOf("[play:a or play:word]") != -1) {
                outPut = "Enter character or word";
             }

            EditText txt = findViewById(R.id.txtStatus);
            txt.append(outPut + "\n");
        }
    }


    private class OutputHandler implements ShowInputFromServer {
        @Override
        public synchronized void show(String s) {

            //runOnUiThread(new Runnable() {
                //@Override
                //public void run() {
                    //updateGUI(s);
                    new OutputHandler2().execute(s);
                //}
            //});
        }
    }

    public class OutputHandler2 extends AsyncTask<String, Void, String> {

        @Override
        public String doInBackground(String... param) {
            String result = param[0];
            return result;
        }

        @Override
        public void onPostExecute(String result) {
            updateGUI(result);
        }
    }
}
