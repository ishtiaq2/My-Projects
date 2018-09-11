import java.util.concurrent.*;

public class CompletableFutureTests {
	public static void main(String[] args) throws Exception {
		RunParallelFutures rpf = new RunParallelFutures();
		Thread t = new Thread(rpf);
		t.start();
		
		for (int i = 0; i < 10; i++ ) {
			try {
				System.out.println(Thread.currentThread().getName() + ": " + i);
				Thread.sleep(500);
			} catch (Exception e) {}
		}
		
		
		
	}
}