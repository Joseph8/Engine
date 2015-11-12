package csc481.objects;

import java.io.Serializable;

public class MoverNoGravity extends Mover implements Serializable{
	private static final long serialVersionUID = -6656135759979328453L;

	public MoverNoGravity(float xSpeed, float ySpeed, GameObject obj) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		owner = obj;
	}
	
	public void move() {
		//move
		owner.xPos += xSpeed;
		owner.yPos += ySpeed;
		
		//move to opposite boundary
		if (owner.xPos > owner.parent.width) {
			owner.xPos = 0;
		} else if (owner.xPos < 0) {
			owner.xPos = owner.parent.width;
		}
		
		if (owner.yPos > owner.parent.height) {
			owner.yPos = 0;
		} else if (owner.yPos < 0) {
			owner.yPos = owner.parent.height;
		}
	}
}

