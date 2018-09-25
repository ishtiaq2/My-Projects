import java.io.*;

public class CounterResources {
    
    CounterService service;
    
	public CounterResources(CounterService service) {
		this.service = service;
	}
	
	public File[] processRequest(String request, int clientId) {
		File[] file = new File[1];
		try {
		//file[0] = new File("header.html");
		/*PrintStream out = new PrintStream("user.html");
		out.println("Welcome to NIO Http Server");
		out.println("</br>");
		out.println("Your ID: " + clientId);
		out.println("</body></html>");
		out.flush();
		out.close();*/
		file[0] = new File("." + "/dist/index.html");
		//file[1] = new File("." + "/dist/styles.css");
				
		return file;
		} catch(Exception e) {
			System.out.println("Error in processRequest: " + e);
		}
		return null; /** define later */
	}
    //@POST
    //@Path("/createcounter")
    public Counter createCounter(Counter counter) {
        try {
            String response = service.addCounter(counter);
            if (response.equals("created")) {
                return new Counter(counter.getName() + " " + response, 0);
            } else {
                return new Counter(counter.getName(), counter.getCounterValue());
            }
        } catch (Exception e){
            System.out.println("Error..." + e);
            return new Counter("Can not create " + counter.getName(), 0);
        }        
    }
    
    //@PUT
    //@Path("/updatecountervalue/{name}")
    public Counter updateCounterValue( /**@PathParam("name")*/ String name) {
        String response = "";
        try {
            response = service.incrementCounterValue(name);
            if (response.equals("incremented")) {
                return new Counter(name + " incremented by 1", 0);
            }
        } catch (Exception e) {
            System.out.println("Error in update");
        }
        return new Counter(name + " unable to update", 0);
    }
    
    //@GET
    //@Path("/getcountervalue/{name}")
    public Counter getCounterValue( /**@PathParam("name")*/ String name ) {
        try {
            return new Counter("Name: " + name + 
                    ", Value: " + service.getCounterValue(name), 0);
        } catch (Exception e) {
            System.out.println("Error in getcountervalue");
        }
        return new Counter(name + " Error getting value", 0);
    }
    
    //@GET
    //@Path("/getlistofcounter") 
    //@Produces(MediaType.APPLICATION_JSON)
    public Counter[] getListOfCounter() {
        try {
            return service.getListOfCounter();
        } catch (Exception e) {
            System.out.println("Error in getlistof counter");
        }
        
        return null;
    }
        
    //@DELETE
    //@Path("/deletecounter/{name}")
    public Counter deleteCounter(/*@PathParam("name")*/ String name) {
        try {
            String response = service.deleteCounter(name);
            if (response.equals("deleted")) {
                return new Counter(name + " " + response, 0);
            } else {
                return new Counter(name + " " + response, 0);
            }
        
        } catch(Exception e) {
            System.out.println("Error in delete");
            return new Counter(name + " can not delete", 0);
        }
    }
}