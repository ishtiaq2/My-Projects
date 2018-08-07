/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.ishtiaq2.microplayerrank;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ishtiaq
 */
@Stateless
@Path("/playerRankService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerRankService {
    
    @Inject
    private PlayerRankBeanService innerService;
    
    @GET
    @Path("/findById/{id}")
    public PlayerRank getPlayerRankById( @PathParam ("id") String id ) {
        System.out.println(id);
        Integer i = Integer.parseInt(id);
        PlayerRank p = innerService.getPlayerRankById(i);
        if ( p != null) {
            return p;
        }
        return new PlayerRank(i, "Not Exist");
    }
}
