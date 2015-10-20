package csc481.objects;

public class MoverNoGravity extends Mover {
	
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

