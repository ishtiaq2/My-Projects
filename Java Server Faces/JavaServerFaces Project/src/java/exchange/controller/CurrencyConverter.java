/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.controller;

import exchange.model.Exchange;
import java.util.List;

/**
 *
 * @author ishtiaq
 */
public class CurrencyConverter {
    
    private double exchangeRate_fromCurrency = 0;
    private double exchangeRate_toCurrency = 0;
    private String fromCurrency, toCurrency;
        
    public String convertCurrency(List<Exchange> ex, double amount, String from , String to) {
                
        for (Exchange e: ex) {
            
            StringBuilder sbTemp = new StringBuilder();
            sbTemp.append(e);
            
            String[] tokens = sbTemp.toString().split(" ");
            if (tokens[0].equalsIgnoreCase(from)) {
                fromCurrency = from;
                exchangeRate_fromCurrency = Double.parseDouble(tokens[1]);
            }
            
            if (tokens[0].equalsIgnoreCase(to)) {
                toCurrency = to;
                exchangeRate_toCurrency = Double.parseDouble(tokens[1]);
            }
        }
        
        double convert = (exchangeRate_fromCurrency / exchangeRate_toCurrency) * amount;
        String result = amount + " " + fromCurrency.toUpperCase() + " = " + convert + " " + toCurrency.toUpperCase();
        
        return result;                
    }
    
    
}
