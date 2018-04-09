/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.view;

import exchange.controller.ExchangeFacade;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author ishtiaq
 */

@Named("exchangeManager")
@RequestScoped
public class ExchangeManager {
    
    @EJB ExchangeFacade exchangeFacade;
    private double amount = 1;
    private String fromCurrency = "SEK";
    private String toCurrency = "EUR";
    private String exchangedAmount = "";
    String navigate = "";
   
    /**
     * setters methods
     */
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    
    public void settoCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }
        
    /**
     * getters methods
     * @return 
     */
    public double getAmount() {
        return amount;
    }
    public String getFromCurrency() {
        return fromCurrency;
    }
    
    public String getToCurrency() {
        return toCurrency;
    }
    
    public String getExchangedAmount() {
        return exchangedAmount;
    }
    /**
     * convert currency
     */
    
    public String convertCurrency() {
        exchangedAmount = exchangeFacade.convertCurrency(amount, fromCurrency, toCurrency);
        
        if ( exchangedAmount.contains("fail")) {
            navigate = "Fail";
        } else {
            navigate = "Success";
        }
        return navigate;
    }    
    
}
