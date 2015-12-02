package csc481.objects;

import java.io.Serializable;

import csc481.ProcessingSketch;

public class RendererNormal extends Renderer implements Serializable {
	private static final long serialVersionUID = 6561729629105275027L;

	public RendererNormal(GameObject obj) {
		owner = obj;
	}
	
	public void render() {
		ProcessingSketch.getInstance().fill(owner.color);
		ProcessingSketch.getInstance().rect(owner.xPos, owner.yPos, owner.width, owner.height, owner.corner_radius);	
	}

}
