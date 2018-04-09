package exchange.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ishtiaq
 */
@Entity
public class Exchange implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String countryCode, exchangeRate;
    
    public Exchange() {
    
    }
    
    public Exchange(Integer id, String cCode, String eRate){
        this.id = id;
        countryCode = cCode;
        exchangeRate = eRate;
    }
    
   
    @Override
    public String toString() {
        return countryCode + " " + exchangeRate;
    }
    
}
