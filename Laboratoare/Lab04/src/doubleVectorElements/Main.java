package doubleVectorElements;

/**
 * @author cristian.chilipirea
 *
 */
public class Main {
	static int N = 100000013;
	static int v[] = new int[N];
	static int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
	public static void main(String[] args) {
		
		Thread[] t = new MyThread[NUMBER_OF_THREADS];
		for(int i=0;i<N;i++)
			v[i]=i;
		
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
	   
	
		for (int i = 0; i < N; i++) {
			if(v[i]!= i*2) {
				System.out.println("Wrong answer");
				System.exit(1);
			}
		}
		System.out.println("Correct");
	}

}
