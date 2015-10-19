package csc481.objects;
import processing.core.PApplet;

public class Object {
	protected float xPos;
	protected float yPos;
	protected float xSpeed;
	protected float ySpeed;
	protected int color;
	protected int alpha;     //opacity
	protected PApplet parent;
	
	public Object (PApplet p) {
		parent = p;
		xPos = 0;
		yPos = 0;
		xSpeed = 0;
		ySpeed = 0;
		color = 0;
		alpha = 0;
	}
	
//	void display() {
//		parent.fill((int) parent.random(1,294967295),100);
//		parent.noStroke();
//		//parent.rect(x/2, 0, w/2, parent.height);
//	}
	
//	void move() {
//		xPos += speed;
//		//if (x > parent.width +20) x = -20;
//	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public float getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(float speed) {
		this.xSpeed = speed;
	}
	
	public float getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(float speed) {
		this.ySpeed = speed;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
}
