import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelTasks {
	ExecutorService service = Executors.newFixedThreadPool(10);
	
	String[] users = {"user1", "user2", "user3"};
	int[] sleepTime = {6, 4, 2};
	
	public void runTasks() {
		System.out.println("Running tasks...");
		for(int i = 0; i < 3; i++) {
			String currentUser = users[i];
			int currentSleeptime = sleepTime[i];
			CompletableFuture.supplyAsync(()-> getUser(currentUser, currentSleeptime),
																					service)
								.thenApply(user -> getCreditDetail(user, currentSleeptime))
									.thenApply(email -> sendEmail(email))
									    .thenAccept(System.out::println)
										.thenRun(() -> System.out.println("completed"));
		}
		service.shutdown();
	}
	
	public String getUser(String user, int toSleep) {
		try {
			Thread.currentThread().sleep( toSleep * 1000);
		} catch(Exception e) {}
		System.out.println(Thread.currentThread().getName() + ", getUser(): " + user);
		return user;
	}
	
	public String getCreditDetail(String user, int credits) {
		System.out.println(Thread.currentThread().getName() +", getCreditDetail(): " + user);
		return user + "-Credits: " + credits * 1000;
	}
	
	public String sendEmail(String email) {
		System.out.println("sendEmail: " + email);
		String[] user = email.split("-");
		return "email sent to: " + user[0];
	}
	
}
