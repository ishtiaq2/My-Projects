/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.controller;

import exchange.model.Exchange;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ishtiaq
 */
@Stateless
public class ExchangeFacade {
    private String exchangedAmount = "";
     
    // Injected database connection:
    @PersistenceContext private EntityManager em;
    
    // Stores a new Exchange record: 
    /*public String persist(Exchange ex) {
        em.persist(ex);
        return "Success";
    }*/
           
    public String convertCurrency(double amount, String from, String to) {
        try { 
            TypedQuery<Exchange> query = em.createQuery("SELECT e FROM Exchange e where e.countryCode IN ('" + from  +"'," + "'" +to +"') ", Exchange.class);
            List<Exchange> exchange = query.getResultList();
            exchangedAmount = new CurrencyConverter().convertCurrency(exchange, amount, from, to);
       } catch (Exception e) {
           exchangedAmount = "fail";
       }
        
       return exchangedAmount;
    }
}
