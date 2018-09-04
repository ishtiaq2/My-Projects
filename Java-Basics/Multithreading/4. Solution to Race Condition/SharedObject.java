public class SharedObject {
		int counter = 0;
		public synchronized void setCounter() {
			++counter;
		}
		
		public int getCounter() {
			return counter;
		}
}


/**
	public int getCounter(int value) {
		if (value > max) {
			synchronized(this) {
				max = value;
			}
		}
		return max;
	}
	However, the problem here could be that it might return
	immediately on one request while may process max on another request.
	Interesting to test.
 */