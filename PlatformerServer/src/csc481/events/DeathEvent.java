package csc481.events;

public class DeathEvent extends Event {
	private static final long serialVersionUID = -9011241076065167530L;
	public final int priority = 3;
	
	public DeathEvent(long timestamp) {
		type = EventType.DEATH;
		this.timestamp = timestamp;
	}
}
