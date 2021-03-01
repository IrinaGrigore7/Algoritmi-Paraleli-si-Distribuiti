package doubleVectorElements;

public class MyThread extends Thread{
	int id;
	
	public MyThread(int id) {
		this.id = id;
	}
	
	public void run() {
		 int start = this.id * Main.N / Main.NUMBER_OF_THREADS;
		 int end = Math.min((this.id + 1) * Main.N / Main.NUMBER_OF_THREADS, Main.N);
			// Parallelize me
		for (int i = start; i < end; i++) {
			Main.v[i] = Main.v[i] * 2;
		}

	}

}
