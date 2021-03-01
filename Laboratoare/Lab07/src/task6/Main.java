package task6;

import java.util.concurrent.ForkJoinPool;


public class Main {
    public static int N = 4;
    
    public static void main(String[] args) {
        int[] graph = new int[N];
        int step = 0;
        ForkJoinPool fjp = new ForkJoinPool(4);
		fjp.invoke(new MyTask(graph, step));
		fjp.shutdown();
    }
}
