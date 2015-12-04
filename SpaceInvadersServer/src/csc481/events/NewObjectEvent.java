package csc481.events;

import csc481.Time.Timeline;
import csc481.objects.GameObject;

public class NewObjectEvent extends Event{
	private static final long serialVersionUID = 7056346607601879804L;
	public GameObject newObj;
	public final int priority = 5;
	public NewObjectEvent(GameObject newObj, long timestamp) {
		this.newObj = newObj;
		//this.timestamp = Timeline.getGameTime();
		this.type = EventType.NEW_OBJECT;
		this.timestamp = timestamp;
	}
}
