package csc481.objects;

import java.io.Serializable;

import processing.core.PApplet;
import csc481.ProcessingSketch;

public class MovingPlatform extends GameObject implements Serializable {
	private static final long serialVersionUID = 3800632648653825830L;

	public MovingPlatform() {
		this((float) 0,(float) 0,(float) 0,(float) 0,(float) 0, 0, (float) 0, (float) 0);
	}
	
	public MovingPlatform(float xPos, float yPos, float width, float height, float corner_radius, int color, float xSpeed, float ySpeed) {
		super(xPos, yPos, width, height);
		super.setAlpha(255);
		this.color = color;
		mover = new MoverNoGravity(xSpeed, ySpeed, this);
		collider = null;
		renderer = new RendererNormal((GameObject) this);
	}

}
