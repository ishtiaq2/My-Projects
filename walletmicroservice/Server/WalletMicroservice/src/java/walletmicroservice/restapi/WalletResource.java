package walletmicroservice.restapi;

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
import jsonsupport.JsonResponse;
import walletmicroservice.wallet.Wallet;
import walletmicroservice.wallet.WalletService;

/**
 *
 * @author ishtiaq
 */
@Stateless
@Path("/wallet")
public class WalletResource {
    @Inject WalletService walletService;
    
    
    @Produces("application/json")
    @Path("/getWalletList")
    @GET
    public Wallet[] getWalletList() {
        Wallet[] walletList = walletService.getWalletList();
        return walletList;
    }
    
    @Produces({"application/json"})
    @Path("/getWallet")
    @GET
    public Wallet getWallet(@QueryParam ("id") String walletId) {
        Wallet wallet = walletService.getWallet(walletId);
        if (wallet == null) {
            return new Wallet("NOT FOUND", " ", 0);
        }
        return wallet;
    }
    
    @POST
    @Path("/createWallet")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse createWallet(Wallet wallet) {
        String response = "";
        try {
            response = walletService.createWallet(wallet.getName(), wallet.getCurrentBalance());
            if (response.equalsIgnoreCase("WalletCreated")) {
                return new JsonResponse(wallet.getName(), response, 0);
            }
        } catch(Exception e) {
            System.out.println("Error in Post: " + e);
        }
        return new JsonResponse(wallet.getName(), response, 0);
    }
    
    @PUT
    @Path("/creditWallet/{walletId}/{transactionId}/{amount}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse creditWallet(@PathParam("walletId") String walletId, @PathParam("transactionId") String transactionId, @PathParam("amount") int amount) {
        String response = "";
        try {
            response = walletService.creditWallet(walletId, transactionId, amount);
            if (response.equalsIgnoreCase("WalletCredited")) {
                return new JsonResponse(walletId, response, 0);
            }
        } catch (Exception e) {
            System.out.println("Error in PUT credit: " + e);            
        }
        return new JsonResponse(walletId, response, 0);
    }
    
    @PUT
    @Path("/debitWallet/{walletId}/{transactionId}/{amount}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse debitWallet(@PathParam("walletId") String walletId, @PathParam("transactionId") String transactionId, @PathParam("amount") int amount) {
        String response = "";
        try {
            response = walletService.debitWallet(walletId, transactionId, amount);
            if (response.equalsIgnoreCase("WalletDebited")) {
                return new JsonResponse(walletId, response, 0);
            }
        } catch (Exception e) {
            System.out.println("Error in PUT credit: " + e);            
        }
        return new JsonResponse(walletId, response, 0);
    }
    
    @DELETE
    @Path("/deleteWallet/{walletId}")  //@Path("users/{username: [a-zA-Z][a-zA-Z_0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse deleteWallt(@PathParam("walletId") String walletId) {
        String res = walletService.removeWallet(walletId);
        return new JsonResponse(walletId, "Deleted", 0);   
    }
}
