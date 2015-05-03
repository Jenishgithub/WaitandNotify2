import java.util.ArrayList;
import java.util.List;

public class LockTip extends Thread {
	private List longs = new ArrayList();

	public static void main(String args[]) {
		LockTip lt = new LockTip();
		lt.start();
		new LongSupplier(lt).start();
	}

	public void run() {
		while (true) {
			try {
				synchronized (this) {
					wait();
				}

				// do something with longs
				System.out.println("doing something: " + this.longs);
			} catch (InterruptedException e) {
			}
		}
	}

	public void addLong(Long l) {
		synchronized (this) {

			this.longs.add(l);
			notifyAll();
		}
	}
}

class LongSupplier extends Thread {
	private LockTip lt;

	public LongSupplier(LockTip lt) {
		this.lt = lt;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				this.lt.addLong(new Long(123));
			} catch (InterruptedException e) {
			}
		}
	}
}