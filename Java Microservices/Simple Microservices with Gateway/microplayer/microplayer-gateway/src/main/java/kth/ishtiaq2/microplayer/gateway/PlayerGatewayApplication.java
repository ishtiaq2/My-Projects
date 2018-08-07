/*
 * Learned from: https://www.youtube.com/watch?v=rCRFz_wx6fY&t=440s
 */
package kth.ishtiaq2.microplayer.gateway;

import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author ishtiaq
 */
@Stateless
@ApplicationPath("/playerGatewayApp")
public class PlayerGatewayApplication extends Application {
    
}
