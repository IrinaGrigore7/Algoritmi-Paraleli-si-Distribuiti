package binarySearch;

import java.util.concurrent.BrokenBarrierException;

public class MyThread extends Thread{
		int id;
		volatile static boolean found = false;
		
		
		public MyThread(int id) {
			this.id = id;
		}
		
		public void run() {
			 int start = this.id * Main.N / Main.NUMBER_OF_THREADS;
			 int end = Math.min((this.id + 1) * Main.N / Main.NUMBER_OF_THREADS, Main.N - 1);
			 int newN = 0;
			 while (!found)  { 
				 try {
						Main.barrier.await();
					 } catch (InterruptedException | BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 }
				 
				 if (Main.v[start] == Main.key || Main.v[end] == Main.key)  { 
			            found = true; 
			            System.out.println("Numarul " + Main.key + " a fost gasit");
			        } 
				 
				 if (Main.v[start] < Main.key && Main.v[end] > Main.key) {
					 newN = end - start;
					 
				 }
				 
				 try {
					Main.barrier.await();
				 } catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
				 
				 end = start + Math.min((this.id + 1) * (newN) / Main.NUMBER_OF_THREADS, newN);
				 start = start + this.id * (newN) / Main.NUMBER_OF_THREADS;

			   

			 }
		}
}
