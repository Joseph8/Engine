package csc481.Time;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;



public class Timeline implements Serializable {
	private static final long serialVersionUID = 4879080289871748537L;
	private final int AVG_WINDOW = 20; //frames to average for delta time calculation
	private long startTime;
	private long loopIterations;
	private float timeMultiplier;
	private Queue<Long> prevFrameTimes;
	
	public Timeline() {
		startTime = System.currentTimeMillis();
		loopIterations = 0;
		timeMultiplier = (float) 1.0;
		prevFrameTimes = new LinkedList<Long>();
		for (int i = 0; i < AVG_WINDOW; i++) {
			prevFrameTimes.add((long) 0);
		}
	}
	
	public long getGameTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * 
	 * @return estimated time change this frame
	 */
	public double getDeltaT() {
		Long p[] = (Long[]) prevFrameTimes.toArray();
		int total = 0;
		
		for (int i = 0; i < AVG_WINDOW - 1; i++) {
			total += (p[i+1] - p[i]); //add delta t for each previous frame in the window to the average total
		}
		double average = ((double)total)/(double)(AVG_WINDOW - 1);
		
		return average * timeMultiplier;
		
	}
	
	public void setTimeMultiplier(float mult) {
		timeMultiplier = mult;
	}
	
	public double getTimeMultiplier() {
		return timeMultiplier;
	}
	
	public void incrementIterations() {
		prevFrameTimes.remove();
		prevFrameTimes.add(getGameTime());
		loopIterations++;
	}
	
	public long getIterations() {
		return loopIterations;
	}
}
