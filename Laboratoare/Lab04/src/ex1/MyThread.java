package ex1;

public class MyThread extends Thread{
	 	int id;
	   
	    public MyThread(int id) {
	        this.id = id;
	    }
	 
	    public void run() {
	    	System.out.println("Hello from thread " + id);
	    	
	    }
	 
}
