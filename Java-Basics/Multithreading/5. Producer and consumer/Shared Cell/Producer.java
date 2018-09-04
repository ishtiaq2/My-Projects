import java.util.Scanner;

public class Producer extends Thread {
	private SharedCell cell;
	private boolean running = true;
	
	public Producer(SharedCell cell) {
		this.cell = cell;
	}
	
	public void run() {
		Scanner input = new Scanner(System.in);
		while(running) {
			System.out.println("Enter data to produce or quit to exit");
			String in = input.nextLine();
			cell.setValue(in);
			try {
				sleep(0);
			} catch(InterruptedException e) {}
			
			if( in.equalsIgnoreCase("quit")) {
				running = false;
			}			
		}
	}
}