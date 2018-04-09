/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.ishtiaq.websocket;

/**
 *
 * @author ishtiaq
 */
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author ishtiaq
 */
@ServerEndpoint("/rpsapp")
@ApplicationScoped
@Stateful
public class RPSServer {
    
    @Inject
    RPSSessionHandler sessionHandler;
    
    @OnOpen
    public void open(Session session) {
        try {
            sessionHandler.addSession(session);
        } catch (Exception e) {
            System.out.println("Error in life cycle open: " + e);
        }
    }
    
    @OnMessage
    public void handleMessage(String message, Session session) {
       sessionHandler.handleMessage(message, session);
       
    }
    
    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }
    
    @OnError
        public void onError(Throwable error) {
            System.out.println(error);
    }    
}
