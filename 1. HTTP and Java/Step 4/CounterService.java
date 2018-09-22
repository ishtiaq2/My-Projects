import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**@ApplicationScoped*/
public class CounterService {
    
    Map<String, Counter> counters = new HashMap<>();
    
    public CounterService() {
        addCounter(new Counter("abc", 0));
        addCounter(new Counter("xyz", 0));
    }
    public String addCounter(Counter counter) {
        if (counter.getName().equals("")) {
            return "Choose a Name for Counter";
        }
        Counter exist = counters.get(counter.getName());
        if (exist == null) {
            counters.put(counter.getName(), counter);
            return "created";
        }
        return "Already Exists";
    }
    
    public int getCounterValue(String name) {
        return counters.get(name).getCounterValue();
    }
    
    
    public String incrementCounterValue(String name) {
        Counter exist = counters.get(name);
        if (exist != null) {
            counters.get(name).setCounterValue(1);
            return "incremented";
        } else {
            return "does not exists";
        }
    }
      
    public Counter[] getListOfCounter() {
        List<Counter> counterList = new ArrayList(counters.values());
        return  counterList.toArray(new Counter[counterList.size()]);
    }
    
    public String deleteCounter(String name) {
        Counter exist = counters.get(name);
        if (exist != null) {
            counters.remove(name);
            return "deleted";
        } else {
            return " Not Exists";
        }
    }
}