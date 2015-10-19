package csc481.objects;

import csc481.ProcessingSketch;
import processing.core.PApplet;

public class Player extends Rectangle {
	private static final float maxFallingSpeed = (float) 3.5;
	private static final float jumpSpeed = (float) -3.5;
	private static final float moveSpeed = (float) 2;
	private boolean movingLeft;
	private boolean movingRight;
	private boolean jumping;
	private boolean onGround;
	private boolean secondJumpAvailable;
	
	public Player(PApplet p) {
		this(p,(float) 0,(float) 0,(float) 0,(float) 0,(float) 0);
	}
	
	public Player(PApplet p, float xPos, float yPos, float width, float height, float corner_radius) {
		super(p, xPos, yPos, width, height, corner_radius);
		setXSpeed((float) 1.5);
		movingLeft = false;
		movingRight = false;
		onGround = true;
		secondJumpAvailable = false;
	}
	
	public void move() {
//		if (movedLeft) {
//			movedLeft = false;
//			xPos -= xSpeed;
//		}
//		if (movedRight) {
//			movedRight = false;
//			xPos += xSpeed;
//		}
		
		//set speed
		if (movingLeft) xSpeed = -1 * moveSpeed;
		if (movingRight) xSpeed = moveSpeed;
		if (jumping) {
			jumping = false;
			ySpeed = jumpSpeed;		
		}
		
		//move
		xPos += xSpeed;
		yPos += ySpeed;
		
		//y acceleration
		if (ySpeed < maxFallingSpeed) {
			ySpeed += .1;
			if (ySpeed > maxFallingSpeed) {
				ySpeed = maxFallingSpeed;
			}
		}
		//x acceleration
		if (xSpeed > 0) {
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
		if (xPos > parent.width) {
			xPos = 0;
		} else if (xPos < 0) {
			xPos = parent.width;
		}
		if (yPos > parent.height) {
			yPos = 0;
		} else if (yPos < 0) {
			yPos = parent.height;
		}
	}
	
	public void addMovement(float xSpeed2, float ySpeed2) {
		xPos += xSpeed2;
		if (yPos + ySpeed2 > maxFallingSpeed) {
			yPos += ySpeed2;
		}
	}
	
	public void moveLeft() {
		movingLeft = true;
	}
	
	public void moveRight() {
		movingRight = true;
	}

	public void jump() {
		if (onGround) {
			jumping = true;
			secondJumpAvailable = true;
		} else if (secondJumpAvailable) {
			jumping = true;
			secondJumpAvailable = false;
		}
	}

	public void stopMoveLeft() {
		movingLeft = false;
	}
	
	public void stopMoveRight() {
		movingRight = false;
	}
	
	public void stopJump() {
		jumping = false;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		if (this.onGround == true && onGround == false) {
			secondJumpAvailable = true;
		}
		this.onGround = onGround;
	}
	
}
