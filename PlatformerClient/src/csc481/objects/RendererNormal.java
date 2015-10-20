package csc481.objects;

public class RendererNormal extends Renderer {

	public RendererNormal(GameObject obj) {
		owner = obj;
	}
	
	public void render() {
		owner.parent.fill(owner.color);
		owner.parent.rect(owner.xPos, owner.yPos, owner.width, owner.height, owner.corner_radius);	
	}

}
