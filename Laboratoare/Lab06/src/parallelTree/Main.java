package parallelTree;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {
	static int N_ITERATIONS = 100;
	public static CyclicBarrier barrier;
	
	public static void main(String[] args) {
		Thread[] threads = new Thread[3];
		System.out.println("Parallel tree problem");
		
		for (int j = 0; j < N_ITERATIONS; j++) {
			TreeNode tree = new TreeNode(1);
			threads[0] = new Thread(new ReadTreePart(tree, "C:\\Users\\Irina Grigore\\Desktop\\Facultate   ANUL 3\\Semestrul 1\\APD\\Lab 6\\treePart1.txt"));
			threads[1] = new Thread(new ReadTreePart(tree, "C:\\Users\\Irina Grigore\\Desktop\\Facultate   ANUL 3\\Semestrul 1\\APD\\Lab 6\\treePart2.txt"));
			threads[2] = new Thread(new VerifyTree(tree));
			barrier = new CyclicBarrier(3);
			for (int i = 0; i < 3; i++) {
				threads[i].start();
			}

			for (int i = 0; i < 3; i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}