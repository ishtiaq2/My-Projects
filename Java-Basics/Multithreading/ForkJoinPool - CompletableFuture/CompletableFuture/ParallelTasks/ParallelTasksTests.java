public class ParallelTasksTests {
	public static void main(String[] args) {
		ParallelTasks tasks = new ParallelTasks();
		tasks.runTasks();
		
		for (int i = 0; i < 6; i++ ) {
			try {
				System.out.println(Thread.currentThread().getName() + ": " + i);
				Thread.currentThread().sleep(500);
			} catch(InterruptedException e) {
			}
		}
	}
}