/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.ishtiaq2.microplayerrank;

/**
 *
 * @author ishtiaq
 */
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ishtiaq
 */
@XmlRootElement
public class PlayerRank {
    Integer id;
    String rank;
    
    public PlayerRank() {
    
    }
    
    public PlayerRank(Integer id, String rank) {
        this.id = id;
        this.rank = rank;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setRank(String rank) {
        this.rank = rank;
    }
    
    public String getRank() {
        return rank;
    }
}
