package shortestPathsFloyd_Warshall;

import java.util.concurrent.BrokenBarrierException;

public class MyThread extends Thread {
	int id;
	
	public MyThread(int id) {
		this.id = id;
	}
	
	public void run() {
		 int start = this.id * Main.N / Main.NUMBER_OF_THREADS;
		 int end = Math.min((this.id + 1) * Main.N / Main.NUMBER_OF_THREADS, Main.N);
		// Parallelize me (You might want to keep the original code in order to compare)
		for (int k = 0; k < 5; k++) {
			for (int i = 0; i < 5; i++) {
				for (int j = start; j < end; j++) {
					Main.graph[i][j] = Math.min(Main.graph[i][k] + Main.graph[k][j], Main.graph[i][j]);
				}
				try {
		                //Resincronizarea thread-urilor pentru urmatorul pas al algoritmului.
		                Main.barrier.await();
		        } catch (BrokenBarrierException | InterruptedException e) {
		                e.printStackTrace();
		        }
			}
		}
	}
}
