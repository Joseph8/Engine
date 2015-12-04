package csc481.eventhandlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import csc481.ProcessingSketch;
import csc481.Time.Replay;
import csc481.Time.Timeline;
import csc481.events.CollisionEvent;
import csc481.events.Event;
import csc481.events.EventType;
import csc481.events.NewObjectEvent;
import csc481.events.ShotCollisionEvent;
import csc481.objects.GameObject;

public class EventManager implements Serializable{
	private static final long serialVersionUID = -4608295940501146786L;
	private LinkedList<Event> eventQueue;
	private HashMap<Long, TreeSet<EventType>> handlerMap;
	private static EventManager instance;
	
	public EventManager() {
		eventQueue = new LinkedList<Event>();
		handlerMap = new HashMap<Long, TreeSet<EventType>>();
	}
	
	public void addEvents(LinkedList<Event> newEvents) {
		eventQueue.addAll(newEvents);
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
				if (Replay.recording) {
					Replay.saveEvent(newEvent);
				}
				break;
			} else if (newEvent.timestamp < event.timestamp) {
				continue;
			} else if (newEvent.priority >= event.priority) { //timestamps are equal, check priority
				eventQueue.add(i, newEvent);
				if (Replay.recording) {
					Replay.saveEvent(newEvent);
				}
				break;
			}
			i++;
		}
		if (i == eventQueue.size()) {
			eventQueue.addLast(newEvent);
			if (Replay.recording) {
				Replay.saveEvent(newEvent);
			}
		}
		
	}
	
	public void handleEvents() {
		for (int i = 0; i < eventQueue.size(); i++) {
			Event e = eventQueue.getLast();
			if (e.timestamp > ProcessingSketch.getGameTimeline().getIterations()) break;
			if (e.type == EventType.NEW_OBJECT) {
				ProcessingSketch.addGameObject(((NewObjectEvent) e).newObj);
			} else if (e.type == EventType.SHOT_COLLISION) {
				ProcessingSketch.removeObjByGUID(((ShotCollisionEvent) e).obj1GUID);
				ProcessingSketch.removeObjByGUID(((ShotCollisionEvent) e).obj2GUID);
			} else {
				for (GameObject obj : ProcessingSketch.getObjects()) {
					if (handlerMap.get(obj.getGUID()) == null) continue;
					if (handlerMap.get(obj.getGUID()).contains(e.type)) {
						if (e.type == EventType.COLLISION) {
							if (((CollisionEvent)e).obj1.getGUID() != obj.getGUID() && ((CollisionEvent)e).obj2.getGUID() != obj.getGUID()) { 
								continue; //if obj is neither of the collided objects, don't send the collision event to obj
							}
						}
						obj.onEvent(e);
						
					}
				}
			}
			eventQueue.removeLast();
			System.out.println("Events handled: " + i);
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

	public LinkedList<Event> getEventQueue() {
		return eventQueue;
	}

	public void setEventQueue(LinkedList<Event> eventQueue) {
		this.eventQueue = eventQueue;
	}
	
}
