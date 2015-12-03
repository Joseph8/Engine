package csc481.objects;
import java.io.Serializable;

import csc481.events.Event;
import processing.core.PApplet;

public class GameObject implements Serializable {
	private static final long serialVersionUID = 12345;
	private static long nextGUID = 0;
	private long GUID;
	protected float xPos;
	protected float yPos;
	protected int color;
	protected int alpha;     //opacity
	protected float width;
	protected float height;
	protected float corner_radius;
	protected float prevXBoundLeft;
	protected float prevXBoundRight;
	
	//components
	protected Mover mover;
	protected Collider collider;
	protected Renderer renderer;
	
	public GameObject (PApplet p, float xPos, float yPos, float width, float height) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		color = 0;
		alpha = 0;
		GUID = nextGUID;
		nextGUID++;
	}
	
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
	
	public long getGUID() {
		return GUID;
	}

	public void onEvent(Event event) {
		//should be overridden
	}

	public float getPrevXBoundLeft() {
		return prevXBoundLeft;
	}

	public float getPrevXBoundRight() {
		return prevXBoundRight;
	}

	public void setPrevXBounds() {
		prevXBoundLeft = xPos;
		prevXBoundRight = xPos + width;
	}
}
