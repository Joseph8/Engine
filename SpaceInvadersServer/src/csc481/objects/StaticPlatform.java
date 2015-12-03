package csc481.objects;

import java.io.Serializable;

import processing.core.PApplet;

public class StaticPlatform extends GameObject implements Serializable {
	private static final long serialVersionUID = 8749464222466635478L;

	public StaticPlatform(PApplet p) {
		this(p,(float) 0,(float) 0,(float) 0,(float) 0,(float) 0, 0);
	}
	
	public StaticPlatform(PApplet p, float xPos, float yPos, float width, float height, float corner_radius, int color) {
		super(p, xPos, yPos, width, height);
		super.setAlpha(255);
		this.color = color;
		mover = null;
		collider = null;
		renderer = new RendererNormal((GameObject) this);
	}
}
