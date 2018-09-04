public class SharedCell {
	private String value = "";
	private boolean empty = true;
	
	public synchronized void setValue(String value) {
		while(!empty) {
			try {
				wait();
			} catch(InterruptedException e) {}
		}
		this.value = value;
		empty = false;
		notify();
	}
	
	public synchronized String getValue() {
		while(empty) {			
			try {
				wait();
			} catch(Exception e) {}
		}
		empty = true;
		notify();
		return value;
	}
}