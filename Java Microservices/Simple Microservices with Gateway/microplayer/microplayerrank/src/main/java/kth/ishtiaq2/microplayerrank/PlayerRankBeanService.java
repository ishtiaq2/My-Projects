/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.ishtiaq2.microplayerrank;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author ishtiaq
 */
@ApplicationScoped
public class PlayerRankBeanService {
    
    private Map<Integer, PlayerRank> playersRank = new HashMap<>();
    
    public PlayerRankBeanService() {
        playersRank.put(1, new PlayerRank(1, "First"));
        playersRank.put(2, new PlayerRank(2, "Second"));
    }
    
    public PlayerRank getPlayerRankById(int id) {
        return playersRank.get(id);
    }
    
}
