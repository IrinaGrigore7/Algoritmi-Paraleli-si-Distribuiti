package multipleProducersMultipleConsumers;
/**
 * @author cristian.chilipirea
 *
 */
public class Buffer {
	int[] a;
 	int index;
 	Buffer() {
 		a = new int[6];
 		index = 0;
 	}

 	void put(int value) {
 		synchronized(this) {
 			while (this.isFull()) {
 				try {this.wait();}catch(Exception ex) {}
 			}
 			a[index] = value;
 			index++;
 			this.notifyAll();
 		}
 	}

 	int get() {
 		int aux;
 		synchronized(this) {
 			while (this.isEmpty()) {
 				try {this.wait();}catch(Exception ex) {}
 			}
			index--;
			aux = a[index];
 			this.notifyAll();
			return aux;
 		}
 	}

 	boolean isEmpty() {
 		return index == 0;
 	}

 	boolean isFull() {
 		return index == 6;
 	}	
	
}
