import java.util.Scanner;

public class Boff {

    public static void main(String[] args) {
	
	Scanner in = new Scanner(System.in);
	
	int n = in.nextInt();
	int a = in.nextInt(); 
	int b = in.nextInt();
	int count=0;
	
	if (n>9) {
	    for (int i=a; i<=b; i++) {
	        if ( (i%n==0) || (i%100==n) ) {
		    count++;
		}    
	    }
	} else if (n<10) {
	    for (int i=a; i<=b; i++) {
	        if ( (i%n==0) || (i%10==n) ) {
		    count++;
		}
	    }
	}

	System.out.println(count);
    }
}


