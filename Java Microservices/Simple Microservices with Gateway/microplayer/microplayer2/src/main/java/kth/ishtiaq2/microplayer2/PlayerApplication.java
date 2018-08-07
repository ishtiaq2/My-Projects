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
import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author ishtiaq
 */
@Stateless
@ApplicationPath("/playerApp")
public class PlayerApplication extends Application {
    
}
