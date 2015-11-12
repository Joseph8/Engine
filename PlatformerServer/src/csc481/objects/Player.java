package csc481.objects;

import java.io.Serializable;
import java.util.LinkedList;

import csc481.ProcessingSketch;
import csc481.events.Event;
import csc481.events.InputEvent;
import processing.core.PApplet;

public class Player extends GameObject implements Serializable {
	private static final long serialVersionUID = 3311133245147443568L;
	private static final float jumpSpeed = (float) -3.5;
	private static final float maxMoveSpeed = (float) 4;
	private boolean onGround;
	private boolean secondJumpAvailable;
	private boolean isJumping;
	private int index;
	public float randNum; // for testing

	
	public Player(PApplet p) {
		this(p,(float) 0,(float) 0,(float) 0,(float) 0,(float) 0, 0);
	}
	
	public Player(PApplet p, float xPos, float yPos, float width, float height, float corner_radius, float randNum) {
		super(p, xPos, yPos, width, height);
		//setXSpeed((float) 1.5);
		onGround = true;
		secondJumpAvailable = false;
		isJumping = false;
		mover = new MoverGravityJump((float) 3.5, (GameObject) this);
		collider = new ColliderNormal((GameObject) this);
		renderer = new RendererNormal((GameObject) this);
		this.randNum = index; //for testing
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
	
	public int getIndex() {
		return index;
	}
	
	public void onEvent(Event e) {
		switch (e.type) {
		case COLLISION:
			break;
		case DEATH:
			die();
			break;
		case INPUT:
			switch (((InputEvent)e).input) {
			case JUMP:
				jump();
				break;
			case MOVE_LEFT:
				moveLeft();
				break;
			case MOVE_RIGHT:
				moveRight();
				break;
			case STOP_JUMP:
				stopJump();
				break;
			case STOP_MOVE_LEFT:
				stopMoveLeft();
				break;
			case STOP_MOVE_RIGHT:
				stopMoveRight();
				break;
			default:
				break;
			}
			break;
		case NEW_OBJECT:
			break;
		case SPAWN:
			break;
		default:
			break;
		}
	}
	
}
