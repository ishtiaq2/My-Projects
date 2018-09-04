public class Test {
	public static void main(String[] args) throws Exception {
		PrintName pn1 = new PrintName("One");
		PrintName pn2 = new PrintName("Two");
		Thread t1 = new Thread(pn1);
		Thread t2 = new Thread(pn2);
		t1.start();
		t1.join(); //Blocks the caller thread and wait for t1 to finish
		t2.start();
	}
}
