package csc481.objects;

import java.io.Serializable;

public abstract class Mover implements Serializable {
	private static final long serialVersionUID = 66484;
	protected GameObject owner;
	protected float xSpeed;
	protected float ySpeed;
	/** Return false to signify the item should be removed*/
	public abstract boolean move();
	
	public float getXSpeed() {
		return xSpeed;
	}

	public float getYSpeed() {
		return ySpeed;
	}
	//public void addMovement(float xSpeed, float ySpeed);
}
