/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.ishtiaq2.microplayer2;

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
public class Player {
    Integer id;
    String name;
    
    public Player() {
    
    }
    
    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
