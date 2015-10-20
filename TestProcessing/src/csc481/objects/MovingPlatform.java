package csc481.objects;

import processing.core.PApplet;
import csc481.ProcessingSketch;

public class MovingPlatform extends GameObject {
	
	public MovingPlatform(PApplet p) {
		this(p,(float) 0,(float) 0,(float) 0,(float) 0,(float) 0, 0, (float) 0, (float) 0);
	}
	
	public MovingPlatform(PApplet p, float xPos, float yPos, float width, float height, float corner_radius, int color, float xSpeed, float ySpeed) {
		super(p, xPos, yPos, width, height);
		super.setAlpha(255);
		this.color = color;
		mover = new MoverNoGravity(xSpeed, ySpeed, this);
		collider = null;
		renderer = new RendererNormal((GameObject) this);
	}

}
