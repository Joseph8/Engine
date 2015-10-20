package csc481.objects;

import processing.core.PApplet;
import csc481.ProcessingSketch;

public class Rectangle extends GameObject {
	
	public Rectangle(PApplet p) {
		this(p,(float) 0,(float) 0,(float) 0,(float) 0,(float) 0, (float) 0, (float) 0, 0);
	}
	
	public Rectangle(PApplet p, float xPos, float yPos, float width, float height, float corner_radius, float xSpeed, float ySpeed, int color) {
		super(p, xPos, yPos, width, height);
		super.setAlpha(255);
		this.color = color;
		mover = new MoverNoGravity(xSpeed, ySpeed, this);
	}

}
