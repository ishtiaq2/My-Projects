public class CompletableFutureTests {
	public static void main(String[] args) throws Exception {
		CompletableFutureExamples cfe = new CompletableFutureExamples();
		cfe.runTests();
		
		try {
			for(int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + " : " + i);
				Thread.currentThread().sleep(1000);
			}
			System.out.println("main completed");
		} catch(Exception e) {}
	}
}