public class PrintName implements Runnable {
	String name;
	public PrintName(String name) {
		this.name = name;
	}
	
	public void run() {
		for (int i = 0; i < 20; i++) {
			System.out.println(name);
			Thread.currentThread().yield();
		}
	}
}