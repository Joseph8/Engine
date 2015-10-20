package csc481.objects;

import processing.core.PApplet;

public class DeathZone extends GameObject {
	
	public DeathZone(PApplet p) {
		this(p,(float) 0,(float) 0,(float) 0,(float) 0,(float) 0);
	}
	
	public DeathZone(PApplet p, float xPos, float yPos, float width, float height, float corner_radius) {
		super(p, xPos, yPos, width, height);
		super.setAlpha(0);
		this.color = 0;
		mover = null;
		collider = null;
		renderer = null;
	}
}

