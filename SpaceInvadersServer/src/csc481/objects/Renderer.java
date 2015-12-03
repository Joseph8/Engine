package csc481.objects;

import java.io.Serializable;

public abstract class Renderer implements Serializable {
	private static final long serialVersionUID = 126004058106872503L;
	//texture...
	protected GameObject owner;
	public abstract void render();
}
