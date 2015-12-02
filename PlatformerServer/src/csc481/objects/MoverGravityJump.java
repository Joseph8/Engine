package csc481.objects;

import java.io.Serializable;

import processing.core.PApplet;
import csc481.ProcessingSketch;
import csc481.Time.Timeline;

/**
 * Moves objects that respond to gravity, can jump, and move left and right.
 * @author Joseph Gregory
 *
 */
public class MoverGravityJump extends Mover implements Serializable {
	private static final long serialVersionUID = 6774092702344730030L;
	private float maxFallingSpeed;
	protected boolean movingRight;
	protected boolean movingLeft;
	protected float targetXSpeed;
	
	public MoverGravityJump(float maxFallingSpeed, GameObject obj) {
		xSpeed = 0;
		ySpeed = 0;
		movingRight = false;
		movingLeft = false;
		targetXSpeed = 0;
		this.maxFallingSpeed = maxFallingSpeed;
		owner = obj;
	}
	
	public void move() {
		
		
		//move
		owner.xPos += (xSpeed*ProcessingSketch.getGameTimeline().getTimeMultiplier());
		owner.yPos += (ySpeed*ProcessingSketch.getGameTimeline().getTimeMultiplier());
		
		//y acceleration
		if (ySpeed < maxFallingSpeed) {
			ySpeed += .1;
			if (ySpeed > maxFallingSpeed) {
				ySpeed = maxFallingSpeed;
			}
		}
		//x acceleration
		if (movingRight && xSpeed < targetXSpeed) {
			xSpeed += .1;
		} else if (movingLeft && xSpeed > targetXSpeed) {
			xSpeed -= .1;
		} else if (xSpeed > 0) {
			xSpeed -= .1;
			if (xSpeed < 0) {
				xSpeed = 0;
			}
		} else if (xSpeed < 0) {
			xSpeed += .1;
			if (xSpeed > 0) {
				xSpeed = 0;
			}
		}

		//move to opposite boundary
		if (owner.xPos > ProcessingSketch.getInstance().width) {
			owner.xPos = 0;
		} else if (owner.xPos < 0) {
			owner.xPos = ProcessingSketch.getInstance().width;
		}
		if (owner.yPos > ProcessingSketch.getInstance().height) {
			owner.yPos = 0;
		} else if (owner.yPos < 0) {
			owner.yPos = ProcessingSketch.getInstance().height;
		}
	}
	
	//moves the object if it is on another moving object
	public void addMovement(float xSpeed2, float ySpeed2) {
		owner.xPos += xSpeed2;
		if (owner.yPos + ySpeed2 > maxFallingSpeed) {
			owner.yPos += ySpeed2;
		}
	}
	
	public void setXSpeed(float speed) {
		xSpeed = speed;
	}
	
	public void setYSpeed(float speed) {
		ySpeed = speed;
	}
}
