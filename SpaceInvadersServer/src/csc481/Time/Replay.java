package csc481.Time;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import csc481.ProcessingSketch;
import csc481.eventhandlers.EventManager;
import csc481.events.Event;
import csc481.objects.GameObject;

public class Replay {
	static LinkedList<GameObject> recObjs;
	static LinkedList<GameObject> savedObjs;
	static EventManager recEM;
	static EventManager savedEM;
	static Timeline recTimeline;
	static Timeline savedTimeline;
	static LinkedList<Event> savedEventQueue;
	
	public static long replayEndTime;
	public static boolean recording;
	public static boolean replaying;
	
	public static void startRecording() {
		recording = true;
		recObjs = (LinkedList<GameObject>) deepCopy(ProcessingSketch.getObjects());
		recTimeline = (Timeline) deepCopy(ProcessingSketch.getGameTimeline());
		recEM = (EventManager) deepCopy(ProcessingSketch.getEventManager());
		savedEventQueue = new LinkedList<Event>();
	}
	
	public static void stopRecording() {
		recording = false;
		 // need to add events on the fly instead
		replayEndTime = ProcessingSketch.getGameTimeline().getGameTime();
	}
	
	public static void startReplay() {
		replaying = true;
		savedObjs = (LinkedList<GameObject>) deepCopy(ProcessingSketch.getObjects());
		savedEM = (EventManager) deepCopy(ProcessingSketch.getEventManager());
		savedTimeline = (Timeline) deepCopy(ProcessingSketch.getGameTimeline());
		ProcessingSketch.setObjects(recObjs);
		ProcessingSketch.setEventManager(recEM);
		ProcessingSketch.setGameTime(recTimeline);
		ProcessingSketch.getEventManager().addEvents(savedEventQueue);
		
	}
	
	public static void stopReplay() {
		replaying = false;
		ProcessingSketch.setObjects(savedObjs);
		ProcessingSketch.setEventManager(savedEM);
		ProcessingSketch.setGameTime(savedTimeline);
	}
	
	public static void changeSpeed(boolean up) {
		double mult = ProcessingSketch.getGameTimeline().getTimeMultiplier();
		if (up) {
			if (mult == 2) {
				
			} else if (mult == 1) {
				ProcessingSketch.getGameTimeline().setTimeMultiplier(2);
			} else if (mult == 0.5) {
				ProcessingSketch.getGameTimeline().setTimeMultiplier(1);
			}
		} else {
			if (mult == 2) {
				ProcessingSketch.getGameTimeline().setTimeMultiplier(1);
			} else if (mult == 1) {
				ProcessingSketch.getGameTimeline().setTimeMultiplier((float) 0.5);
			} else if (mult == 0.5) {
				
			}
		}
	}

    public static Object deepCopy(Object original) {
        Object copy = null;
        try {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(byteOutput);
            output.writeObject(original);
            output.flush();
            output.close();
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteOutput.toByteArray()));
            copy = in.readObject();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return copy;
    }

	public static void saveEvent(Event newEvent) {
		savedEventQueue.addFirst(newEvent);
	}
	
}
