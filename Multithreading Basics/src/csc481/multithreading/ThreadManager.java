package csc481.multithreading;

/**
 * A singleton class that handles all the forks
 * @author Joseph Gregory
 *
 */
public class ThreadManager {
	private static final int MAX_THREADS = 10;
	private Fork[] threads;
	private int size;
	private static ThreadManager manager;

	private ThreadManager() {
		size = 0;
		threads = new Fork[MAX_THREADS];
	}
	
	public static ThreadManager getInstance() {
		if (manager == null) {
			manager = new ThreadManager();
		}
		return manager;
	}
	/**
	 * Returns true if the thread was added.
	 * Returns false if the next thread created will be the last in threads[].
	 */
	public boolean addThread(Fork thread) {
		if (size >= MAX_THREADS-1) {
			return false;
		}
		threads[size] = thread;
		size++;
		return true;
	}
	
	public Fork getAtIndex(int idx) {
		return threads[idx];
	}
	
	public Fork getTheBusyThread() {
		for (int i = 0; i < size; i++) {
			if (threads[i].isBusy()) return threads[i];
		}
		return null;
	}

	public boolean haveAllThreadsRun() {
		for (int i = 0; i < size; i++) {
			if (!threads[i].hasRun()) return false;
		}
		for (int i = 0; i < size; i++) {
			threads[i].hasRun = false;
		}
		return true;
	}
}
