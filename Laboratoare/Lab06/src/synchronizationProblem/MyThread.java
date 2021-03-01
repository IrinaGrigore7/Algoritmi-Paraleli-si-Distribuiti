package synchronizationProblem;

import java.util.concurrent.atomic.AtomicInteger;

public class MyThread implements Runnable {
	private final int id;
	public static AtomicInteger sum ;
	
	public MyThread(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		int start = id * (int) Math.ceil((double) 2 * Main.N / 2);
        int end = Math.min(2 * Main.N, (id + 1) * (int) Math.ceil((double) 2 * Main.N / 2));
		for(int i = start; i < end; i++) {
			sum.getAndAdd(3);
		}
		
	}
}
