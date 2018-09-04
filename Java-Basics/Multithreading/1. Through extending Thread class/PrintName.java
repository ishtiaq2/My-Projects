import java.util.*;

public class PrintName extends Thread {
	public PrintName(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(getName());
			yield(); //context switch between threads
											//tells the thread scheduler that the 
											//currentThread can pause for if something //important is happening on another thread.
		}
	}
}
	
	