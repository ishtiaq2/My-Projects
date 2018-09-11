import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RunParallelFutures implements Runnable {
	
	String comparer = "Hello Beautiful World";
	
	CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> getHello());
	CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> getBeautiful());
	CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> getWorld());
	
	public String getHello() {
		for (int i = 0; i < 3; i++ ) {
			try {
				System.out.println(Thread.currentThread().getName() + ", Hello: " + i);
				Thread.sleep(1000);
			} catch (Exception e) {}
		}
		return "Hello";
	}
	public String getBeautiful() {
		for (int i = 0; i < 3; i++ ) {
			try {
				System.out.println(Thread.currentThread().getName() + ", Beautful: " + i);
				Thread.sleep(1000);
			} catch (Exception e) {}
		}
		return "Beautiful";
	}
	public String getWorld() {
		for (int i = 0; i < 3; i++ ) {
			try {
				System.out.println(Thread.currentThread().getName() + ", World: " + i);
				Thread.sleep(1000);
			} catch (Exception e) {}
		}
		return "World";
	}
	
	public void run() {
		try {
				System.out.println(Thread.currentThread().getName() + ", CompleClass: ");
			} catch (Exception e) {}
		
		String combined = Stream.of(future1, future2, future3)
						.map(CompletableFuture::join)
							.collect(Collectors.joining(" "));
  
		boolean test = assertEquals(comparer, combined);
		System.out.println(comparer + " = " + combined + " : " + test);	
	}
	  
  
	public boolean assertEquals(String comparer, String toCompare) {
		if (toCompare.equals(comparer)) {
			return true;
		}
		return false;
  }
	
}