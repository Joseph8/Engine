package csc481.objects;

import processing.core.PApplet;
import csc481.ProcessingSketch;

public class Rectangle extends Object {
	protected float width;
	protected float height;
	protected float corner_radius;
	public Rectangle(PApplet p) {
		this(p,(float) 0,(float) 0,(float) 0,(float) 0,(float) 0);
	}
	
	public Rectangle(PApplet p, float xPos, float yPos, float width, float height, float corner_radius) {
		super(p);
		super.setxPos(xPos);
		super.setyPos(yPos);
		super.setAlpha(255);
		this.width = width;
		this.height = height;
		this.corner_radius = corner_radius;
	}
	
	public void display() {
		parent.fill(color);
		parent.rect(xPos, yPos, width, height, corner_radius);
	}
	
	public void move() {
		xPos += xSpeed;
		if (xPos > parent.width) {
			xPos = 0;
		} else if (xPos < 0) {
			xPos = parent.width;
		}
		yPos += ySpeed;
		if (yPos > parent.height) {
			yPos = 0;
		} else if (yPos < 0) {
			yPos = parent.height;
		}
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
	
	

}
