package csc481.events;

import java.io.Serializable;

public abstract class Event implements Serializable {
	private static final long serialVersionUID = -713584300120176634L;
	public EventType type;
	/**
	 * The iteration the event was raised on
	 */
	public long timestamp;
	public final int priority = 0;
}
