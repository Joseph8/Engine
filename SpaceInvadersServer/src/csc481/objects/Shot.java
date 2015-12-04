package csc481.objects;

import processing.core.PApplet;

public class Shot extends GameObject {
	private static final long serialVersionUID = -2106338986979336518L;
	static int num_shots;
	public Shot(float xPos, float yPos, float width, float height, float speed) {
		super(xPos, yPos, width, height);
		num_shots++;
		mover = new MoverShot(0, speed, this);
		collider = new ColliderNormal(this);
		renderer = new RendererNormal(this);
	}
	public void remove() {
		num_shots--;
	}
}