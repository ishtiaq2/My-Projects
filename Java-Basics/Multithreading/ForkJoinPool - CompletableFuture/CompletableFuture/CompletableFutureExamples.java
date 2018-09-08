import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableFutureExamples {
	
	//ExecutorService executor = Executors.newSingleThreadExecutor();
	ExecutorService executor = Executors.newFixedThreadPool(4); //optional
	
	Runnable CF1 = () -> {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		System.out.println(Thread.currentThread().getName() + ", **I am CF1**");
	};
	
	Supplier<String> supplier1 = () -> {
		return Thread.currentThread().getName() + ", I am supplier1";
	};
	
	public void futureCF1() {
		CompletableFuture<Void> cf1 = CompletableFuture.runAsync(CF1);
	}
	
	public void futureCF2() {
		CompletableFuture<Void> cf2 = CompletableFuture.runAsync(new CF2(), executor)
									.thenRun(() -> System.out.println("CF2 is done"));
	}
									
	public String futureCF3() throws Exception {									
		CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() ->
								Thread.currentThread().getName() + ", I am supplier0");
		return cf3.get();
	}
	
	public String futureCF4() throws Exception {									
		CompletableFuture<String> cf4 = CompletableFuture.supplyAsync(supplier1);
		return cf4.get();
	}
	
	public String futureCF5() throws Exception {									
		CompletableFuture<String> cf5 = CompletableFuture.supplyAsync(new Supplier2());
		return cf5.get();
	}
	
	public void futureCF6() throws Exception {									
		CompletableFuture<String> user = CompletableFuture.supplyAsync(() -> getUser())
									.thenApplyAsync(usr -> usr + ": " + getCredit(usr))
									.thenApplyAsync(cred -> cred + " temp");
		CompletableFuture<Void> printer = user.thenAccept(System.out::println);
		CompletableFuture<Void> creditProcessed = printer.thenRun(() -> System.out.println("Transaction completed"));
		user.get();
	}
	
	public String getUser() {
		//get user from remote server;
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		System.out.println("user returned");
		return "ISHTIAQ";
	}
	public String getCredit(String user) {
		//get credit from remote server;
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		System.out.println("credit returned");
		return "Credit : 1000";
	}
	public void runFutures() throws Exception {
		//cf1.get();
		futureCF1();
		futureCF2();
		//System.out.println(cf3.get());
		//System.out.println(cf4.get());
		//System.out.println(cf5.get());
		//System.out.println("error: " + creditProcessed.get());
		//executor.shutdown();
		//connect();
		futureCF6();
		executor.shutdown();
	}
		
	class CF2 implements Runnable {
		public void run() {
			try {
				for (int i = 0; i < 5; i++) {
					System.out.println(Thread.currentThread().getName() + ", I am CF2");
					Thread.sleep(1000);
				}
            } catch (Exception ioe) {
                
            }
		}
	}
	
	class Supplier2 implements Supplier<String> {
		public String get() {
			return Thread.currentThread().getName() + ", I am supplier2";
		}
	}
	
}