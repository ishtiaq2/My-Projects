public class Test {
	public static void main(String[] args) {
		PrintName t1 = new PrintName("Thread One");
		PrintName t2 = new PrintName("Thread Two");
		t1.start();
		t1.join(); //Blocks the caller thread and wait for t1 to finish
		t2.start();
	}
}
		