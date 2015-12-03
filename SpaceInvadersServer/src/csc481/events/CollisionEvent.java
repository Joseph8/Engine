package csc481.events;

import csc481.objects.GameObject;

public class CollisionEvent extends Event {
	private static final long serialVersionUID = 9064787736342704051L;
	public final int priority = 4;
	public GameObject obj1;
	public GameObject obj2;
	public CollisionEdge edge;
	
	public CollisionEvent (GameObject obj1, GameObject obj2, CollisionEdge edge, long timestamp) {
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.edge = edge;
		type = EventType.COLLISION;
		this.timestamp = timestamp;
	}
}
