package csc481.objects;

import java.io.Serializable;

public abstract class Collider implements Serializable {
	private static final long serialVersionUID = 6356499062630208485L;
	protected GameObject owner;
	//protected int priority;
	protected float prevXBounds[] = new float[4];
	
	//PRIOITY COLLISION
	/**
	//collide where only this object changes velocity
	public abstract void wallCollide(GameObject obj2);
	
	//collide where both objects hit and 
	public abstract void equalCollide(GameObject obj2);
	*/
	
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
	
//	public int getPriority() {
//		return priority;
//	}

}
