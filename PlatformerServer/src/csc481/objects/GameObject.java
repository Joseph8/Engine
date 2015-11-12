package csc481.objects;
import java.io.Serializable;

import processing.core.PApplet;

public class GameObject implements Serializable {
	private static final long serialVersionUID = 12345;
	protected float xPos;
	protected float yPos;
	protected int color;
	protected int alpha;     //opacity
	protected float width;
	protected float height;
	protected float corner_radius;
	protected transient PApplet parent;
	
	//components
	protected Mover mover;
	protected Collider collider;
	protected Renderer renderer;
	
	public GameObject (PApplet p, float xPos, float yPos, float width, float height) {
		parent = p;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
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
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
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

	public Mover getMover() {
		return mover;
	}
	
	public Collider getCollider() {
		return collider;
	}

	public Renderer getRenderer() {
		return renderer;
	}
}
