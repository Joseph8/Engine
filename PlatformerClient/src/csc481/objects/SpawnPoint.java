package csc481.objects;

import processing.core.PApplet;

public class SpawnPoint extends GameObject {
	
	public SpawnPoint(PApplet p) {
		this(p,(float) 0,(float) 0);
	}
	
	public SpawnPoint(PApplet p, float xPos, float yPos) {
		super(p, xPos, yPos, 0, 0);
		super.setAlpha(0);
		this.color = 0;
		mover = null;
		collider = null;
		renderer = null;
	}
}
