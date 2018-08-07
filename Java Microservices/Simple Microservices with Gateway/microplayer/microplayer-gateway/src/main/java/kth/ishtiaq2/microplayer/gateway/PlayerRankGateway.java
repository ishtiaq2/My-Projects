/*
 * Learned from: https://www.youtube.com/watch?v=rCRFz_wx6fY&t=440s
 */
package kth.ishtiaq2.microplayer.gateway;

/**
 *
 * @author ishtiaq
 */
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author ishtiaq
 */
@Stateless
@Path("/playerRankGatewayService")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerRankGateway {
    
    private final String hostURI = "http://localhost:8080/";
    private Client client;
    private WebTarget target;
    
    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target(hostURI + "microplayerrank/");
    }
    
    @GET
    @Path("/findById/{id}")
    public Response getPlayerById( @PathParam ("id") String id ) {
        WebTarget service = target.path("playerRankApp/playerRankService/findById/" + id);
        Response response;
        try {
            response = service.request().get();
        } catch (ProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.fromResponse(response).build();
    }
}
