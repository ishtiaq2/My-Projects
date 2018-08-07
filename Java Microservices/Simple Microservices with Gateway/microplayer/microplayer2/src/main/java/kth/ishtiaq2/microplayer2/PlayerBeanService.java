/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.ishtiaq2.microplayer2;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author ishtiaq
 */
/**
 * 
 * An object which is defined as @ApplicationScoped is created once for the duration of the application.
 * http://tomee.apache.org/examples-trunk/cdi-application-scope/
 */

@ApplicationScoped
public class PlayerBeanService {
    
    private Map<Integer, Player> players = new HashMap<>();
    
    public PlayerBeanService() {
        players.put(1, new Player(1, "Player 1"));
        players.put(2, new Player(2, "Player 2"));
    }
    
    public Player getPlayerById(int id) {
        return players.get(id);
    }
    
}
