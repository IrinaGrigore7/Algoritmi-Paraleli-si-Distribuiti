package shortestPathsFloyd_Warshall;

import java.util.concurrent.CyclicBarrier;

/**
 * @author cristian.chilipirea
 *
 */
public class Main {
	static int M = 9;
	static int N = 5;
	static int graph[][] = { { 0, 1, M, M, M }, 
			          { 1, 0, 1, M, M }, 
			          { M, 1, 0, 1, 1 }, 
			          { M, M, 1, 0, M },
			          { M, M, 1, M, 0 } };
	static int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
	static CyclicBarrier barrier;
	
	public static void main(String[] args) {
		
		Thread[] t = new MyThread[NUMBER_OF_THREADS];
		barrier = new CyclicBarrier(NUMBER_OF_THREADS);
		
	    for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
	    	t[i] = new MyThread(i);
	    	t[i].start();
	    }
	    
	    for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.println();
		}
	}
}
