package csc481.multithreading;
// Demonstrating multithreading and thread synchronization in Java
public class Fork implements Runnable {

	int idx; // the ID of the thread, so we can control behavior
	boolean busy; // the flag, Thread 1 will wait until Thread 0 is no longer busy before continuing
	ThreadManager manager;
	Object monitor;
	boolean hasRun;
	// create the runnable object
	public Fork(int idx, ThreadManager manager, Object monitor) {
		this.idx = idx; // set the thread ID (0 or 1)
		busy = false;
		this.manager = manager;
		hasRun = false;
		this.monitor = monitor;
	}

	// synchronized method to test if thread is busy or not
	public synchronized boolean isBusy() { return busy; } // What happens if this isn't synchronized? 

	public synchronized boolean hasRun() { return hasRun; }
	
	// run method needed by runnable interface
	public void run() {
		while (true) {
			try {
				synchronized(manager) {
					
					System.out.println("Thread " + idx + " is running.");
					Thread.sleep(500);
					
	//					Fork theBusyThread = manager.getTheBusyThread();
	//					if (theBusyThread == null) {
	//						busy = true;
	//					} else {
	//						
	//					}
					hasRun = true;
					manager.notify(); 
				}
				//if all threads have had a chance to run, start a new cycle
				if (manager.haveAllThreadsRun()) {
					System.out.println("Starting a new cycle");
					synchronized(monitor) {
						monitor.notifyAll();
					}
				} else {
					synchronized(monitor) {
						monitor.wait();
					}
				}
			} catch(InterruptedException tie) { tie.printStackTrace(); }
		}
	}

	public static void main(String[] args) {
		ThreadManager manager = ThreadManager.getInstance();
		Object monitor = new Object();
		Fork f;
		int i = 0;
		do {
			f = new Fork(i, manager, monitor);
			(new Thread(f)).start();
			i++;
		} while (manager.addThread(f));
	}

}