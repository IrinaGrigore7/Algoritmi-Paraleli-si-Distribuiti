package ex1;

public class Main {
	public static void main(String[] args) {
		   int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
		   Thread[] t = new MyThread[NUMBER_OF_THREADS];
	 
	       for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
	    	   t[i] = new MyThread(i);
	    	   t[i].start();
	       }
	
	}
}
