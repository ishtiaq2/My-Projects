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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ishtiaq
 */
@Stateless
@Path("/playerService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerService {
    
    @Inject
    private PlayerBeanService innerService;
    
    @GET
    @Path("/findById/{id}")
    public Player getPlayerById( @PathParam ("id") String id ) {
        System.out.println(id);
        Integer i = Integer.parseInt(id);
        Player p = innerService.getPlayerById(i);
        if ( p != null) {
            return p;
        }
        return new Player(i, "Not Exist");
    }
}
