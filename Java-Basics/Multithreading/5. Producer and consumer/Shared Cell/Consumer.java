public class Consumer extends Thread {
	private SharedCell cell;
	private boolean running = true;
	
	public Consumer(SharedCell cell) {
		this.cell = cell;
	}
	
	public void run() {
		while(running) {
			String consume = cell.getValue();
			if (consume.equalsIgnoreCase("quit")) {
				running = false;
			}
			System.out.println("Consumer: " + consume);
			try {
				sleep(0);
			} catch(InterruptedException e) {
			}
		}
	}
}