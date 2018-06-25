public class Recursion {
	
	public static void main(String[] args) {
		int number = 3;
		System.out.println("n! = " + number + "! = " + fac(number));
	}
	
	public static int fac(int number) {
		if (number == 0 ) {
				return 1;
		} else {
			return number * fac(number - 1);
		}
	}
}