/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.ishtiaq.game;

import home.ishtiaq.websocket.RPSSessionHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

/**
 *
 * @author ishtiaq
 */
@ApplicationScoped
public class HandleGame {
    
    @Inject
    RPSSessionHandler sessionHandler;
    
    HashMap<String, Session> map;
    HashMap<String, Integer> circle = new HashMap<>();
    HashMap<String, String> options = new HashMap<>();
    Session tempSession; 
    
    public void registerInterest(String message, Session session, HashMap map) {
        tempSession = session;
        this.map = map;
        String[] messageTokens = message.split(",");
        circle.put(messageTokens[1], 0);
        
        String[] opponents = new String[messageTokens.length - 2];
        
        for (int i = 0; i < opponents.length; i++ ) {
            opponents[i] = messageTokens[i+2];
        }
        for (String s: opponents) {
            sendRequest(s, messageTokens[1]);
        }
    }   
    
    public void sendRequest(String opponent, String user) {
        Set set = map.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Map.Entry me = (Map.Entry)iterator.next();
                String s = (String)me.getKey();
                if ( s.equals(opponent) ) {
                    Session session = (Session)me.getValue();
                    try {
                        sessionHandler.sendRawMessage(session, user +" want to Play");
                    } catch (Exception e) {
                        System.out.println(e);   
                    }
                }
            }
    }
    
    public void chooseWinner(String message) {
        String[] option = message.split(",");
        options.put(option[1], option[2]);  //username, option (R,P,S)
        if (options.size() == circle.size()) {
            OptionsComparer optionsComparer = new OptionsComparer();
            int[] score = optionsComparer.makeDecision(options);     //int[] score = .makeDecision(options) + compare(String[] compare)
            sendScore(score);
            options.clear(); 
        }
    }
    
    
    
    public void sendScore(int[] score) {
        String tosend = "";
        int i = 0;
        Set set = options.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
                String name = (String)me.getKey();
                tosend += name +": Round Score: " + score[i] +" ";
                tosend += "Total Score: " +totalScore(name, score[i]) + "<br/>";
                i++;                
        }
        sessionHandler.updateSessions("score", "temp", tempSession, tosend);
    }
    
    public String totalScore(String name, int currentScore) {
        int totalScore = 0;
        Set set = circle.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            String key = (String) me.getKey();
                if(key.equals(name)) {
                    int previousScore = (Integer) me.getValue();
                    totalScore = previousScore + currentScore;
                    circle.put((String) me.getKey(), totalScore);
                }
        }
        return String.valueOf(totalScore); 
    }
    
    public void remove(String user) {
        circle.remove(user);
        options.remove(user);
    }
}
