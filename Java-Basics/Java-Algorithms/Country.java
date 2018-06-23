import java.util.*;

public class Country implements Comparable<Country> {

    String name;
    int population;
	
    public Country(String n, int pop) {
	name = n;
	population = pop;
    }
	
    public String toString() {
	return name + " " +population;
    }

    public int compareTo(Country c) {
	if (this.population==c.population)
	    return (c.name).compareTo(this.name);
	    else if (this.population > c.population)
	        return 1;
	    else return -1;
    }
    


    public static void main(String[] args) {
	
	
	List list=new ArrayList<Country>();
	
	Scanner in = new Scanner(System.in);
	
	int c = in.nextInt();
	
	for (int i=0; i<c; i++) {
	    String name = in.next();
	    int n = in.nextInt();
	Country nam = new Country(name, n);
	list.add(nam);
	}
	
	int c2 = in.nextInt();
	
	for (int i=0; i<c2; i++) {
	    String name = in.next();
	    int n = in.nextInt();
	    
	    for (int j=0; j<list.size(); j++) {
	        Country cc = (Country)list.get(j);
		if (name.equals(cc.name)) {
		cc.population = cc.population + n;
	        }
	    }
	
	}

	System.out.println();

	Collections.sort(list);
	
	Iterator it = list.iterator();
	
	/*while(it.hasNext()) {
	    System.out.println(it.next());
	}*/

	for (int i=list.size()-1; i>=0; i--) {
	    System.out.println(list.get(i));
	}
	
    }


}
	   
