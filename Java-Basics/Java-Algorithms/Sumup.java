import java.util.*;
import java.util.Collection;
public class Sumup {

    public static void main(String[] args) {
	
	int count=0;
	int sum=0;
	
	Scanner in1 = new Scanner(System.in);
	int a = Integer.parseInt(in1.nextLine());
	
	if (a%2==0) {
	    count=a/2;
	} else count=(a/2)+1;
	
	String s = in1.nextLine();
	Integer[] array = new Integer[a];
	Scanner in = new Scanner(s);
	
	for (int i=0; i<a; i++) {
	    array[i]=Integer.parseInt(in.next());
	}
	
	Arrays.sort(array, Collections.reverseOrder());
	
	for (int i=0; i<count; i++) {
	    sum+=array[i];
	}
	System.out.println(sum);

    }
}
        
	
