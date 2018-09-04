public class Test {
	
	public static void main(String[] args) {
		BoundedBuffer cell = new BoundedBuffer(3);
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