package csc481.eventhandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

import csc481.ProcessingSketch;
import csc481.Time.Timeline;
import csc481.events.Event;
import csc481.events.EventType;
import csc481.objects.GameObject;

public class EventManager {
	private LinkedList<Event> eventQueue;
	private HashMap<Long, TreeSet<EventType>> handlerMap;
	private static EventManager instance;
	
	private EventManager() {
		eventQueue = new LinkedList<Event>();
		handlerMap = new HashMap<Long, TreeSet<EventType>>();
	}
	
	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		return instance;
	}
	
	public void addEvents(ArrayList<Event> newEvents) {
		for (Event newEvent : newEvents) {
			raise(newEvent);
		}
	}
	/**
	 * Add a new event to the eventQueue where it belongs determined first
	 * by its timestamp, then by its priority if timestamps are equal
	 * @param newEvent
	 */
	public void raise(Event newEvent) {
		int i = 0;
		for (Event event : eventQueue) {
			if (newEvent.timestamp > event.timestamp) {
				eventQueue.add(i, newEvent);
				break;
			} else if (newEvent.timestamp < event.timestamp) {
				continue;
			} else if (newEvent.priority >= event.priority) { //timestamps are equal, check priority
				eventQueue.add(i, newEvent);
				break;
			}
			i++;
		}
		if (i == eventQueue.size()) {
			eventQueue.addLast(newEvent);
		}
		
	}
	
	public void handleEvents() {
		for (int i = 0; i < eventQueue.size(); i++) {
			if (eventQueue.getLast().timestamp > Timeline.getIterations()) break;
			for (GameObject obj : ProcessingSketch.getObjects()) {
				if (handlerMap.get(obj.getGUID()).contains(eventQueue.getLast().type)) {
					obj.onEvent(eventQueue.getLast());
					eventQueue.removeLast();
				}
			}
			System.out.println("Event handled: " + i);
		}
	}
	
	public void register (long objGUID, EventType type) {
		TreeSet<EventType> types = handlerMap.get(objGUID);
		if (types == null) {
			types = new TreeSet<EventType>();
		}
		types.add(type);
		handlerMap.put(objGUID, types);
	}
}
