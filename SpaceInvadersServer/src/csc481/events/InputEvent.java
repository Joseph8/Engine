package csc481.events;

import csc481.Time.Timeline;

public class InputEvent extends Event {
	private static final long serialVersionUID = -5307640135543387370L;
	public InputType input;
	public final int priority = 2;

	public InputEvent(InputType input, long timestamp) {
		this.input = input;
		//this.timestamp = Timeline.getIterations();
		type = EventType.INPUT;
		this.timestamp = timestamp;
	}
}
