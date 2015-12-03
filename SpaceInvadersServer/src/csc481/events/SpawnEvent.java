package csc481.events;

import csc481.objects.GameObject;

public class SpawnEvent extends Event {
	private static final long serialVersionUID = 1141062766084605388L;
	public final int priority = 1;
	public GameObject spawnedObj;
	
	public SpawnEvent(GameObject spawnedObj, long timestamp) {
		type = EventType.DEATH;
		this.timestamp = timestamp;
		this.spawnedObj = spawnedObj;
	}
}
