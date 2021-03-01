package multipleProducersMultipleConsumers;

import java.util.concurrent.ArrayBlockingQueue;

public class Buffer {
	int value;
	ArrayBlockingQueue<Integer> array = new ArrayBlockingQueue<Integer>(7);
	
	
	void put(int value) throws InterruptedException {
		array.put(value);
		
	}

	int get() throws InterruptedException {
		return array.take();
	}
}
