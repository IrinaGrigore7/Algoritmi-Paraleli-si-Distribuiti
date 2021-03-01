package oneProducerOneConsumer;
/**
 * @author gabriel.gutu@upb.ro
 *
 */
public class Buffer {
	int a = -1;

	void put(int value) {
		synchronized (this) {
			while(a > -1) {
				try {
					this.wait();
					} catch(Exception ex) {}
			}
			a = value;
			this.notify();
		}
	}

	int get() {
		int aux;
		synchronized (this) {
			while(a == -1) {
				try {
					this.wait();
					} catch(Exception ex) {}
			}
			aux = a;
			a = -1;
			this.notify();
			return aux;
		}
	}
}
