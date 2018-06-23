import java.util.Scanner;

public class DToEdge {
    public static void main(String[] args) {

	Scanner in = new Scanner(System.in);
		
	int rows = in.nextInt();
	int cols = in.nextInt();

	for (int r=0; r < rows; r++) {
	    for (int c=0; c < cols; c++) {
	
		int dr = Math.min(r, rows - (1 + r));  	
		int dc = Math.min(c, cols - (1 +c));	
		int n = Math.min(dr, dc)+1;		 
		
		if (n>9) System.out.print(".");
		    else System.out.print(n);
	
	    }
	    System.out.println();
	}
    }
}


//http://stackoverflow.com/questions/8278806/square-for-loop-pattern
