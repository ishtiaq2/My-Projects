public class OperateOnSharedObj implements Runnable {
	SharedObject sb;
	public OperateOnSharedObj(SharedObject sb) {
		this.sb = sb;
	}
	
	public void run() {
		for (int i = 0; i < 5000; i++ ) {
			sb.setCounter();
		}
	}
}