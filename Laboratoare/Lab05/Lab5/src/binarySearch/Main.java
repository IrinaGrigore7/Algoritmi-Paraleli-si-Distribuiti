package binarySearch;

import java.util.concurrent.CyclicBarrier;

public class Main {
	static int N = 9;
	static int v[] = new int[N];
	static int key = 7;
	static int NUMBER_OF_THREADS = 3;
	public static CyclicBarrier barrier;
	 
	public static void main(String[] args) {
		
		Thread[] t = new MyThread[NUMBER_OF_THREADS];
		barrier = new CyclicBarrier(NUMBER_OF_THREADS);
		
		for(int i = 0; i < N; i++)
			v[i] = i;
		
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
	   
	}

}
