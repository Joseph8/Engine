package csc481.objects;

import java.util.LinkedList;

import csc481.ProcessingSketch;
import processing.core.PApplet;

public class Player extends GameObject {
	private static final float jumpSpeed = (float) -3.5;
	private static final float maxMoveSpeed = (float) 4;
	private boolean onGround;
	private boolean secondJumpAvailable;
	private boolean isJumping;

	
	public Player(PApplet p) {
		this(p,(float) 0,(float) 0,(float) 0,(float) 0,(float) 0);
	}
	
	public Player(PApplet p, float xPos, float yPos, float width, float height, float corner_radius) {
		super(p, xPos, yPos, width, height);
		//setXSpeed((float) 1.5);
		onGround = true;
		secondJumpAvailable = false;
		isJumping = false;
		mover = new MoverGravityJump((float) 3.5, (GameObject) this);
		collider = new ColliderNormal((GameObject) this);
		renderer = new RendererNormal((GameObject) this);
	}
	
	public void moveLeft() {
		((MoverGravityJump)mover).targetXSpeed = -1 * maxMoveSpeed;
		((MoverGravityJump)mover).movingLeft = true;
	}
	
	public void moveRight() {
		((MoverGravityJump)mover).targetXSpeed = maxMoveSpeed;
		((MoverGravityJump)mover).movingRight = true;
	}

	public void jump() {
		if (onGround) {
			((MoverGravityJump)mover).ySpeed = jumpSpeed;
			secondJumpAvailable = true;
			isJumping = true;
		} else if (!isJumping && secondJumpAvailable) {
			((MoverGravityJump)mover).ySpeed = jumpSpeed;
			secondJumpAvailable = false;
		}
	}

	public void stopMoveLeft() {
		((MoverGravityJump)mover).movingLeft = false;
	}
	
	public void stopMoveRight() {
		((MoverGravityJump)mover).movingRight = false;
	}
	
	public void stopJump() {
		isJumping = false;
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
	
	public void die() {
		LinkedList<GameObject> objects = ProcessingSketch.getObjects();
		for (GameObject obj : objects) {
			if (obj instanceof SpawnPoint) {
				xPos = obj.getxPos();
				yPos = obj.getyPos();
			}
		}
	}
	
}
