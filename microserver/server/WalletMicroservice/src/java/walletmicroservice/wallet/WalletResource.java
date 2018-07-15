/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package walletmicroservice.wallet;

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
    public Wallet createWallet(Wallet wallet) {
        String result = "";
        try {
            result = walletService.createWallet(wallet.getName(), wallet.getCurrentBalance());
            if (result.equalsIgnoreCase("walletCreated")) {
                return new Wallet("", wallet.getName()+ "Created", 0);
            }
        } catch(Exception e) {
            System.out.println("Error in Post: " + e);
        }
        return new Wallet(" ", result, 0);   
    }
    
    @PUT
    @Path("/creditWallet/{walletId}/{transactionId}/{amount}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Wallet creditWallet(@PathParam("walletId") String walletId, @PathParam("transactionId") String transactionId, @PathParam("amount") int amount) {
        String res = walletService.creditWallet(walletId, transactionId, amount);
        return new Wallet(" ", res, 0);
    }
    
    @PUT
    @Path("/debitWallet/{walletId}/{transactionId}/{amount}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Wallet debitWallet(@PathParam("walletId") String walletId, @PathParam("transactionId") String transactionId, @PathParam("amount") int amount) {
        String res = walletService.debitWallet(walletId, transactionId, amount);
        return new Wallet(" ", res, 0);
    }
    
    @DELETE
    @Path("/deleteWallet/{walletId}")  //@Path("users/{username: [a-zA-Z][a-zA-Z_0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Wallet deleteWallt(@PathParam("walletId") String id) {
        String res = walletService.removeWallet(id);
        return new Wallet(" ", res, 0);
        
    }
}
