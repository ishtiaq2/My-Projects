package ishtiaq2.kth.nfsapp.user;

import ishtiaq2.kth.nfsapp.jsonsupport.Register;
import ishtiaq2.kth.nfsapp.jsonsupport.UpdateUser;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/user")
public class UserResource {
    @Inject UserService userService;
   
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Register registerUser(Register register) {
        String result = "";
        try {
            System.out.println("Post Request: " + register.getName() + " : " + register.getPassword());
            result = userService.createUser(register);
            if (result.equalsIgnoreCase("usercreated")) {
                return new Register("User", "Created");
            }
        } catch(Exception e) {
            System.out.println("Error in Post: " + e);
        }
        return new Register("User with this name already exist", ", choose another user name");   
    }

    //@Produces({"application/xml", "application/json"})
    @Produces("application/json")
    @Path("/login")
    @GET
    public Register getUser(@QueryParam("name") String name, @QueryParam("password") String password) {
        String[] response = userService.getUser(name, password);
        if (!response[0].contains("loggedin")) {
            return new Register(name, " NOT FOUND");
        }
        return new Register(response[0], response[1] + response[2] );
    }
    
    @Produces("application/json")
    @Path("/logout")
    @GET
    public Register getUser(@QueryParam("uid") String uid) {
        System.out.println("Get Request (Logout): " + uid);
        String[] response = userService.logOutUser(uid);
        return new Register(response[0], response[1] + response[2] );
    }
    
    
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Register updateUser(UpdateUser updateUser) {
        String res = "";
        try {
            System.out.println("Update User Request: " + updateUser.getName() + " : " + updateUser.getPassword());
            res = userService.updateUser(updateUser);
        } catch(Exception e) {
            System.out.println("Error in Update: " + e);
        }
        if (res.equals("updated")) {
            return new Register("User Password ", "Changed");   
        } else {
            return new Register(updateUser.getName(), " NOT FOUND");   
        }
    }
    
    @DELETE
    @Path("/delete/{name}/{password}")  //@Path("users/{username: [a-zA-Z][a-zA-Z_0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Register deleteUser(@PathParam("name") String name, @PathParam("password") String password) {
        String res = "";
        try {    
            System.out.println("Delete Request: " + name + " : " + password);
            res = userService.removeUser(name, password);
        } catch(Exception e) {
            System.out.println("Error in Update: " + e);
        }
        if (res.equals("removed")) {
            return new Register("User", "Deleted");   
        } else {
            return new Register("User", "NOT FOUND");   
        }
    }
    
    @Produces("application/json")
    @Path("/makeAdmin")
    @PUT
    public Register makeAdmin(Register user) {
        System.out.println("Admin Request For : " + user.getName());
        String response = userService.makeAdmin(user);
        return new Register(response, "");
    }
    
    
    /*@POST
    //@Consumes("application/x-www-form-urlencoded")
    public Response addStock(@FormParam("symbol") String symb,
                             @FormParam("currency") String currency,
                             @FormParam("price") String price,
                             @FormParam("country") String country) {

        if (StockService.getStock(symb) != null)
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Stock " + symb +
                    " already exists").type("text/plain").build();

        double priceToUse;
        try {
            priceToUse = new Double(price);
        }
        catch (NumberFormatException e) {
            priceToUse = 0.0;
        }

        StockService.addStock(new Stock(symb, priceToUse,
                                                  currency, country));

        return Response.ok().build();
    }*/
}