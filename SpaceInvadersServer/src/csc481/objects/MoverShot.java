package csc481.objects;

import java.io.Serializable;

import csc481.ProcessingSketch;

public class MoverShot extends Mover implements Serializable{
	private static final long serialVersionUID = -6666372948024170377L;

	public MoverShot(float xSpeed, float ySpeed, GameObject obj) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		owner = obj;
	}
	
	public boolean move() {
		//move
		owner.xPos += (xSpeed*ProcessingSketch.getGameTimeline().getTimeMultiplier());
		owner.yPos += (ySpeed*ProcessingSketch.getGameTimeline().getTimeMultiplier());
		
		//move to opposite boundary
		if (owner.xPos > ProcessingSketch.getInstance().width) {
			owner.xPos = 0;
		} else if (owner.xPos < 0) {
			owner.xPos = ProcessingSketch.getInstance().width;
		}
		
		if (owner.yPos > ProcessingSketch.getInstance().height || owner.yPos < 0) {
			return false;
		}
		return true;
	}
}