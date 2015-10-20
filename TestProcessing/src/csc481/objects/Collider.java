package csc481.objects;

public abstract class Collider {
	protected GameObject owner;
	protected int priority;
	protected float prevXBounds[] = new float[4];
	
	public abstract void collide(GameObject obj2);

	public float[] getPrevXBounds() {
		return prevXBounds;
	}
	
	public void setPrevOwnerXBounds() {
		prevXBounds[0] = owner.xPos;
		prevXBounds[1] = owner.xPos + owner.height;
	}
	
	public void setPrevObj2XBounds(GameObject obj2) {
		prevXBounds[2] = obj2.getxPos();
		prevXBounds[3] = obj2.getxPos() + obj2.getWidth();
		
	}
	
	public int getPriority() {
		return priority;
	}

}
