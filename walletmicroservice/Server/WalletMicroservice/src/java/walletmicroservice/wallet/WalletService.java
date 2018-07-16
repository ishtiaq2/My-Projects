package walletmicroservice.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
/**
 *
 * @author ishtiaq
 */
@ApplicationScoped
public class WalletService {
    
    private Map<String, Wallet> wallets = new HashMap<String, Wallet>();
    private Map<String, String> transactions = new HashMap<String, String>();
    int walletIdCounter = 1;
    int transactionIdCounter = 0; 
    
    public WalletService() {
       String defaultWallet1 = createWallet("xyz0", 100);
       String defaultWallet2 = createWallet("xyz1", 200);
    }
    
    public synchronized String createWallet(String name, int balance) {
        try {
            String walletId = "abc0" + walletIdCounter;
            addWallet(new Wallet(walletId , name, balance));
            walletIdCounter++;
            return "WalletCreated"; 
        } catch (Exception e) {
            return "Error";
        }
        
    }
    
    public void addWallet(Wallet wallet) {
        wallets.put(wallet.getId(), wallet);
    }
    
    public synchronized String removeWallet(String walletId) {
        wallets.remove(walletId);
        return "temporary";
    }
    
    public Wallet[] getWalletList() {
        List<Wallet> walletList = new ArrayList<>();
        wallets.entrySet().forEach((entry) -> {
            walletList.add(entry.getValue());
        });
        Wallet[] wallets = walletList.toArray(new Wallet[walletList.size()]);
        return wallets;
    }
    
    public Wallet getWallet(String walletId) {
        return wallets.get(walletId);
    }
    
    public String debitWallet(String walletId, String transactionId, int amount) {
        try {
            Wallet wallet = getWallet(walletId);
            if (wallet.getCurrentBalance() >= amount) {
                String transId = getTransactionId(transactionId);
                wallet.setCurrentBalance(wallet.getCurrentBalance() - amount);
                registerTransaction(transId, wallet);
            }
            return "WalletDebited";
        } catch (Exception e) {
            return "Error";
        }
    }
    
    public String creditWallet(String walletId, String transactionId, int amount) {
        try {
            Wallet wallet = getWallet(walletId);
            wallet.setCurrentBalance(wallet.getCurrentBalance() + amount);
            String transId = getTransactionId(transactionId);
            registerTransaction(transId, wallet);
            return "WalletCredited";
        } catch (Exception e) {
            return "Error";
        }        
    }
    
    public synchronized String getTransactionId(String transactionId) {
        transactionIdCounter++;
        String transId = transactionId + transactionIdCounter;
        return transId;
    }
    
    public void registerTransaction(String transId, Wallet wallet) {
        transactions.put(transId, wallet.getId());
    }
}
