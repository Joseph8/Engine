package csc481.objects;

public class Alien extends GameObject {
	private static final long serialVersionUID = 1179587389707473595L;
	public static float spacer;

	public Alien(float xPos, float yPos, float width, float height, int color, float spacer) {
		super(xPos, yPos, width, height);
		this.color = color;
		renderer = new RendererNormal((GameObject) this);
		mover = new MoverAlien(100, 10, spacer,(GameObject) this);
	}
	
	public static void setSpacer(float s) {
		spacer = s;
	}
}
