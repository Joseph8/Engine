package csc481.events;

public class ShotCollisionEvent extends Event {
	private static final long serialVersionUID = 9064787736342704051L;
	public final int priority = 4;
	public long obj1GUID;
	public long obj2GUID;
	
	public ShotCollisionEvent (long obj1GUID, long obj2GUID, long timestamp) {
		this.obj1GUID = obj1GUID;
		this.obj2GUID = obj2GUID;
		type = EventType.SHOT_COLLISION;
		this.timestamp = timestamp;
	}
}
