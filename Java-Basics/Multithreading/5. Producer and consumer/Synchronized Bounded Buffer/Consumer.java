public class Consumer extends Thread {
	private BoundedBuffer cell;
	private boolean running = true;
	
	public Consumer(BoundedBuffer cell) {
		this.cell = cell;
	}
	
	public void run() {
		while(running) {
			String consume = cell.getValue();
			if (consume.equalsIgnoreCase("quit")) {
				running = false;
			}
			System.out.println("Consumed: " + consume);
			try {
				sleep(5000);
			} catch(InterruptedException e) {
			}
		}
	}
}