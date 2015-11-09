package csc481.events;

import java.io.Serializable;

public abstract class Event implements Serializable {
	private static final long serialVersionUID = -713584300120176634L;
	public EventType type;
	public long timestamp;
}
