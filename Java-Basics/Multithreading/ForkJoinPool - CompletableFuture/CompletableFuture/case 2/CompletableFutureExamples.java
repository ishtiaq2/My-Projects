import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.Scanner;

public class CompletableFutureExamples {
	
	volatile boolean finished = false;
	volatile boolean finished2 = false;
	String userName = "Test User";
	//ExecutorService executor = Executors.newSingleThreadExecutor();
	ExecutorService executor = Executors.newFixedThreadPool(4); //optional
	
	
	public void futureCF6() throws Exception {
		System.out.println("Enter user");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		CompletableFuture<String> user = CompletableFuture.supplyAsync(()-> getUser(name), executor) 
			.thenApplyAsync(usr -> getCredit(usr))
				.thenApplyAsync(cred -> cred + doTransaction(cred), executor);
		CompletableFuture<Void> printer = user.thenAcceptAsync(System.out::println);
		CompletableFuture<Void> creditProcessed = printer.thenRunAsync(() -> System.out.println("Transaction completed"));
		/** All these methods execute their tasks in the same pool of threads as the upstream task (user)
		 An async method executes its task in the default fork/join pool, unless it takes an Executor, in which case, the task will be executed in this Executor.
		 */
		
		user.get(); //this is blocking
	}
	
	public String getUser(String name) {
		//get user from remote server;
		userName = name;
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		System.out.println("User " + name + " Found!");
		return name;
	}
	
	public String getCredit(String user) {
		//get credit from remote server;
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		System.out.println(user + " Credit Detail Request!");
		return "Credit:1000";
	}
	
	public String doTransaction(String credit) {
		try {
			Thread.sleep(2000);
		} catch(Exception e) {
		}
		String[] tokens = credit.split(":");
		System.out.println("Transaction request!");
		return ", After transaction 1000 - 100 = 900";
	}
		
	public void test1() {
		CompletableFuture.runAsync(() -> {
			try {
				for(int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " : " + i);
					Thread.currentThread().sleep(1000);
				}
			} catch(Exception e) {}
		}, executor).thenRun(() -> System.out.println("test1 completed"));
	}
	
	
	public void test2() {
		CompletableFuture.runAsync(() -> {
			try {
				for(int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + " : " + i);
					Thread.currentThread().sleep(1000);
				}
			} catch(Exception e) {}
		}, executor).thenRun(() -> System.out.println("test2 completed"));
	}
	
	public void runTests() throws Exception {
		test1();
		test2();		
		futureCF6(); //user.get() is blocking, it will block main thread and test1 and //test2 if executed (test1, 2) later.
		/*futureCF1();
		futureCF2();*/
		executor.shutdown();
		/*while( !cf0.isDone() ) {
			try {wait...
		}*/
	}
	
	//********************************************************************************
	Runnable CF1 = () -> {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		System.out.println(Thread.currentThread().getName() + " CF1: " + userName);
	};
	
	Supplier<String> supplier1 = () -> {
		return Thread.currentThread().getName() + ", I am supplier1";
	};
	
	
	CompletableFuture<Void> cf0 = CompletableFuture.runAsync(() -> {
		try {
			Thread.sleep(3000);
			System.out.println("cf0: connected to server");
		} catch(Exception e) { }
	}).thenRun(()-> System.out.println("cf0: has finished"));
	
	public void futureCF1() {
		CompletableFuture.runAsync(CF1, executor)
									  .thenRun(() -> System.out.println("CF1: has done"));
	}
	
	public void futureCF2() {
		CompletableFuture<Void> cf2 = CompletableFuture.runAsync(new CF2(), executor)
									.thenRunAsync(() -> System.out.println("CF2 is done"));
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