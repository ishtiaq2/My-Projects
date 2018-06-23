import java.util.Scanner;

public class AddWords {

    public static void main(String[] args) {
	
	String s = "";
	Scanner in = new Scanner(System.in);
	int count = in.nextInt();
		
	for (int c = 0; c < count; c++) {
	     s+=in.next();
        }

        System.out.println(s);
    }
}

	    
