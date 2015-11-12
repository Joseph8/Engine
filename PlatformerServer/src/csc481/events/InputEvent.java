package csc481.events;

import csc481.Time.Timeline;

public class InputEvent extends Event {
	private static final long serialVersionUID = -5307640135543387370L;
	public InputType input;

	public InputEvent(InputType input) {
		this.input = input;
		this.timestamp = Timeline.getGameTime();
		this.type = EventType.INPUT;
	}
}
