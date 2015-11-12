package csc481.objects;

import java.io.Serializable;

public class RendererNormal extends Renderer implements Serializable {
	private static final long serialVersionUID = 6561729629105275027L;

	public RendererNormal(GameObject obj) {
		owner = obj;
	}
	
	public void render() {
		owner.parent.fill(owner.color);
		owner.parent.rect(owner.xPos, owner.yPos, owner.width, owner.height, owner.corner_radius);	
	}

}
