public class Fibonacci {
	
	public static void main(String[] args) {
		
		int fiboNumber = Integer.parseInt(args[0]);
		Long result = 1L;
		Long previous = 0L;
		long beforeTime = System.currentTimeMillis();
		for (int i = 1; i < fiboNumber; i++) {
			Long calculate = result + previous;
			System.out.println(calculate);
			previous = result;
			result = calculate;			
		}
		System.out.println("Total Time in MilliSecond Taken ->  "+ (System.currentTimeMillis() - beforeTime));
	}
}