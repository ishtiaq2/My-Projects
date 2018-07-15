/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package walletmicroservice.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

/**
 *
 * @author ishtiaq
 */
@ApplicationScoped
public class WalletService {
    
    private Map<String, Wallet> wallets = new HashMap<String, Wallet>();
    int walletIdCounter = 1;
    String transactionId = ""; //walletid + wallet transaction counter;
    public WalletService() {
       String temp = createWallet("defaultwallet", 100);
       String temp2 = createWallet("defaultwallet", 200);
    }
    public void addWallet(Wallet wallet) {
        wallets.put(wallet.getId(), wallet);
    }
    
    public String removeWallet(String walletId) {
        wallets.remove(walletId);
        return "wallet deleted";
    }
    
    public Wallet[] getWalletList() {
        List<Wallet> walletList = new ArrayList<>();
        for(Map.Entry<String, Wallet> entry : wallets.entrySet()) {
        walletList.add(entry.getValue());
    }
        Wallet[] wallts = walletList.toArray(new Wallet[walletList.size()]);
        return wallts;
    }
    public Wallet getWallet(String walletId) {
        return wallets.get(walletId);
    }
    
    public synchronized String createWallet(String name, int balance) {
        addWallet(new Wallet(name + walletIdCounter, name, balance));
        walletIdCounter++;
        return "walletCreated";
    }
    
    public String debitWallet(String walletId, String transactionId, int amount) {
        Wallet wallet = getWallet(walletId); // and debit amount
        if (wallet.getCurrentBalance() >= amount)
        wallet.setCurrentBalance(wallet.getCurrentBalance() - amount);
        return "temp";
    }
    
    public String creditWallet(String walletId, String transactionId, int amount) {
        Wallet wallet = getWallet(walletId); // and credit amount
        wallet.setCurrentBalance(wallet.getCurrentBalance() + amount);
        return "temp";
    }
}
