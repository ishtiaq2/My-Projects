package jsonsupport;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ishtiaq
 */
@XmlRootElement
public class JsonResponse {
    String walletName = "";
    String response = "";
    private int currentBalance = 0;
    
    public JsonResponse() {
    }
    
    public JsonResponse(String walletName, String response, int currentBalance) {
        this.walletName = walletName;
        this.response = response;
        this.currentBalance = 0;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public String getWalletName() {
        return walletName;
    }
    
    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
    
    public int getCurrentBalance() {
        return 0;
    }
    
    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = 0;
    }
}
