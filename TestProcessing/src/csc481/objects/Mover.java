package csc481.objects;

public abstract class Mover {
	protected GameObject owner;
	protected float xSpeed;
	protected float ySpeed;
	
	public abstract void move();
	
	public float getXSpeed() {
		return xSpeed;
	}

	public float getYSpeed() {
		return ySpeed;
	}
	//public void addMovement(float xSpeed, float ySpeed);
}
