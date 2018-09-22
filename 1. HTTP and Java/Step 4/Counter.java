import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Counter {
    private String name = "";
    private int counterValue = 0;
       
    public Counter() {
    }
    
    public Counter(String name, int counterValue) {
        this.name = name;
        this.counterValue = counterValue;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public synchronized int getCounterValue() {
        return counterValue;
    }
    
    public synchronized void setCounterValue(int counterValue) {
        this.counterValue += counterValue;
    }
}