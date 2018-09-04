public class Test {
	
	public static void main(String[] args) {
		SharedCell cell = new SharedCell();
		Producer producer = new Producer(cell);
		Consumer consumer = new Consumer(cell);
		producer.start();
		consumer.start();
		try {
			consumer.join();
		} catch(InterruptedException e) {
			producer.interrupt();
		}
	}
}