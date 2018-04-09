/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.ishtiaq.websocket;

import home.ishtiaq.game.HandleGame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

/**
 *
 * @author ishtiaq
 */
@ApplicationScoped
public class RPSSessionHandler {
    
    @Inject
    HandleGame handleGame;
    
    private final Set<Session> sessions = new HashSet<>();
    private final List<String> users = new ArrayList<String>();
    HashMap<String, Session> map = new HashMap<String, Session>();
    
    private int numOfSessions = 0;  
    
    public void addSession(Session session) {
        try {
            sessions.add(session);
            numOfSessions++;
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void removeSession(Session session) {
        sessions.remove(session);
        numOfSessions--;
        updateSessions("removeUser", "temp", session, "temp");
    }
    
    public void updateSessions(String cmd, String currentUser, Session session, String fromHandleGame) {
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            Session s = (Session) me.getValue();
            String key = (String) me.getKey();
            sendToSession(s, cmd, key, fromHandleGame);
      }
    }
    
    private void sendToSession(Session session, String cmd, String key, String fromHandleGame) {
        String tosend = ""; 
        if (cmd.equals("newUserAdded")) {
            tosend = cmd +"," +userList(key);
            try {
                if (tosend != null)
                session.getBasicRemote().sendText(tosend);
            } catch (Exception ex) {
                sessions.remove(session);
                map.remove(session);
                Logger.getLogger(RPSSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (cmd.equalsIgnoreCase("removeUser")) {
                tosend = cmd +"," +userList(key);
                try {
                    session.getBasicRemote().sendText(tosend);
                } catch (Exception ex){
                    sessions.remove(session);
                Logger.getLogger(RPSSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        } else if (cmd.equalsIgnoreCase("score")) {
                tosend = cmd +"," +fromHandleGame;
                try {
                    session.getBasicRemote().sendText(tosend);
                } catch (Exception ex){
                    sessions.remove(session);
                Logger.getLogger(RPSSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }
    }
    
    public void handleMessage(String message, Session session) {
        String user = "";
        try {
            String[] messageTokens = message.split(",");
            String cmd = messageTokens[0];
            if (cmd.equals("login")) {
                try {
                    user = messageTokens[1];
                    loginFunction(user, session);
                } catch(Exception e) {
                    System.out.println(e);
                }
            }
            if (cmd.equals("close")) {
                try {
                    user = messageTokens[1];
                    closeFunction(user, session);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (cmd.equals("requestToPlay")) {
                handleGame.registerInterest(message, session, map);
            }
            if(cmd.equals("option")) {
                handleGame.chooseWinner(message);
            }
        } catch (Exception e) {
            System.out.println("Error in handleMessage" + e);
        }
    }
    
    public void loginFunction(String user, Session session) throws IOException {
        boolean exist = checkNameAvailability(user);
        if(!exist) {
            users.add(user);
            map.put(user, session);
            updateSessions("newUserAdded", user, session, "temp");
            session.getBasicRemote().sendText("logedin" +"," + user);
        } else {
            session.getBasicRemote().sendText( user +" already exist");
        }
    }
    
    public void closeFunction(String user, Session session) {
        try {
            users.remove(user);
            map.remove(user);
            handleGame.remove(user);
            sessions.remove(session);
            updateSessions("removeUser", user, session, "temp");
        } catch (Exception e) {
            users.remove(user);
            map.remove(user);
            sessions.remove(session);
        }
    }
    
    public boolean checkNameAvailability(String name) {
        boolean exist = false;
        for (String s: users) {
            if (s.equalsIgnoreCase(name)) {
                exist = true;
                break;
            } 
        }
        return exist;
    }
    
    public String userList(String key) {
        String[] userList = new String[map.size()-1];
        int i = 0;
        Set set = map.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Map.Entry me = (Map.Entry)iterator.next();
                String s = (String)me.getKey();
                if ( !s.equals(key) ) {
                    userList[i] = (String) me.getKey();
                    i++;
                }
            }
        String tosend = String.join(",", userList);
        System.out.println(tosend);
        return tosend;
    }
    
    public void sendRawMessage(Session session, String msg ) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
