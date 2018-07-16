package walletmicroservice.wallet;

import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author ishtiaq
 */
@XmlRootElement
public class Wallet {
    private String id = "";
    private String name = "";
    private int currentBalance = 0;
    
    //private int transactionCounter = 0; //wallet transaction counter
    
    public Wallet() {
    }
    
    public Wallet(String id, String name, int currentBalance) {
        this.id = id;
        this.name = name;
        this.currentBalance = currentBalance;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public synchronized int getCurrentBalance() {
        return currentBalance;
    }
    
    public synchronized void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }
}
