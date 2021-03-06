package csc481.Time;

import java.io.Serializable;
import java.util.Queue;



public class Timeline implements Serializable {
	private static final long serialVersionUID = 4879080289871748537L;
	private static final int AVG_WINDOW = 20; //frames to average for delta time calculation
	private static long startTime;
	private static long loopIterations;
	private static double timeMultiplier;
	private static Queue<Long> prevFrameTimes;
	
	public static void init() {
		startTime = System.currentTimeMillis();
		loopIterations = 0;
		timeMultiplier = 1.0;
		for (int i = 0; i < AVG_WINDOW; i++) {
			prevFrameTimes.add((long) 0);
		}
	}
	
	public static long getGameTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * 
	 * @return estimated time change this frame
	 */
	public static double getDeltaT() {
		Long p[] = (Long[]) prevFrameTimes.toArray();
		int total = 0;
		
		for (int i = 0; i < AVG_WINDOW - 1; i++) {
			total += (p[i+1] - p[i]); //add delta t for each previous frame in the window to the average total
		}
		double average = ((double)total)/(double)(AVG_WINDOW - 1);
		
		return average * timeMultiplier;
		
	}
	
	public static void setTimeMultiplier(double mult) {
		timeMultiplier = mult;		
	}
	
	public static void incrementIterations() {
		prevFrameTimes.remove();
		prevFrameTimes.add(getGameTime());
		loopIterations++;
	}
	
	public static long getIterations() {
		return loopIterations;
	}
}
