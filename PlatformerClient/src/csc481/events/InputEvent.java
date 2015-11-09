package csc481.events;

import csc481.Time.Timeline;

public class InputEvent extends Event {
	public InputType input;

	public InputEvent(InputType input) {
		this.input = input;
		this.timestamp = Timeline.getGameTime();
		this.type = EventType.INPUT;
	}
}
