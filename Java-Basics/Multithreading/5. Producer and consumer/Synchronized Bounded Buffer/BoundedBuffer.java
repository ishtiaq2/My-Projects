public class BoundedBuffer {
	
	private int capacity;
	private int count = 0;
	private int front = 0;
	private int rear = 0;
	private String[] items;
	
	public BoundedBuffer(int capacity) {
		this.capacity = capacity;
		items = new String[capacity];
	}
	
	
	public synchronized void setValue(String value) {
		if(count == capacity) {
			try {
				wait();
			} catch(InterruptedException e) {}
		}
		items[rear] = value;
		rear = (rear + 1) % capacity;
		count++;
		notifyAll();
		
		//future add: return count to inform producer about current buffer.
	}
	
	public synchronized String getValue() {
		if (count == 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		String valu = items[front];
		front = (front + 1) % capacity;
		count--;
		notifyAll();
		return valu;
	}
}