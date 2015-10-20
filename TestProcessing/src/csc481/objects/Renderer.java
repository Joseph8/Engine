package csc481.objects;

import java.io.Serializable;

public abstract class Renderer implements Serializable {
	private static final long serialVersionUID = 6845;
	protected GameObject owner;
	public abstract void render();
}
