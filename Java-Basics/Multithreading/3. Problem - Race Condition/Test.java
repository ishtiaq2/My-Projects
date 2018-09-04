public class Test {
	public static void main(String[] args) throws Exception {
		SharedObject sb = new SharedObject();
		OperateOnSharedObj op1 = new OperateOnSharedObj(sb);
		OperateOnSharedObj op2 = new OperateOnSharedObj(sb);
		Thread t1 = new Thread(op1);
		Thread t2 = new Thread(op2);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(sb.getCounter());
	}
}

		