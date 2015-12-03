package csc481.objects;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

import csc481.ProcessingSketch;
import csc481.events.CollisionEvent;
import csc481.events.Event;
import csc481.events.EventType;
import csc481.events.InputEvent;
import csc481.scripts.ScriptManager;
import processing.core.PApplet;

public class Player extends GameObject implements Serializable {
	private static final long serialVersionUID = 3311133245147443568L;
	public static final float jumpSpeed = (float) -3.5;
	private static final float maxMoveSpeed = (float) 4;
	public boolean onGround;
	public boolean secondJumpAvailable;
	public boolean isJumping;
	private int index;
	public float randNum; // for testing
	public static final String inputScript = "./../scripts/player_input.js";
	public static final String jumpScript = "./../scripts/player_jump.js";
	
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
		ScriptManager.loadScript(jumpScript);
		ScriptManager.bindArgument("player", this);
		ScriptManager.bindArgument("playerMover", mover);
		ScriptManager.executeScript();
		/*
		if (onGround) {
			((MoverGravityJump)mover).ySpeed = jumpSpeed;
			secondJumpAvailable = true;
			isJumping = true;
		} else if (!isJumping && secondJumpAvailable) {
			((MoverGravityJump)mover).ySpeed = jumpSpeed;
			secondJumpAvailable = false;
		}
		*/
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
		ScriptManager.loadScript(inputScript);
		ScriptManager.bindArgument("player", this);
		if (e.type == EventType.COLLISION) {
			CollisionEvent c = (CollisionEvent)e;
			ScriptManager.bindArgument("c", c);
			ScriptManager.bindArgument("isPlayer", c.obj2 instanceof Player);
			ScriptManager.bindArgument("edge", c.edge);
		} else if (e.type == EventType.INPUT) {
			ScriptManager.bindArgument("input", ((InputEvent)e).input);
		}
		ScriptManager.bindArgument("type", e.type);
		ScriptManager.bindArgument("playerMover", mover);
		ScriptManager.executeScript();
		/*
		switch (e.type) {
		case COLLISION:
			CollisionEvent c = (CollisionEvent)e;
			if (c.obj2 instanceof Player) {
				//switch the objects so that obj1 is the player
				Player temp = (Player) c.obj2;
				c.obj2 = c.obj1;
				c.obj1 = temp;				
			} 
			setOnGround(true);
			switch (c.edge) {
			case BOTTOM:
				setyPos(c.obj2.getyPos() + c.obj2.getHeight());
				if (c.obj2.getMover() == null) {
					((MoverGravityJump)getMover()).setYSpeed(0);
				} else {
					((MoverGravityJump)getMover()).setYSpeed(c.obj2.getMover().getYSpeed());
				}
				break;
			case LEFT:
				setxPos(c.obj2.getxPos() - getWidth());
				if (c.obj2.getMover() == null) {
					((MoverGravityJump)getMover()).setXSpeed(0);
				} else {
					((MoverGravityJump)getMover()).setXSpeed(c.obj2.getMover().getXSpeed());
				}
				break;
			case RIGHT:
				setxPos(c.obj2.getxPos() + c.obj2.getWidth());
				if (c.obj2.getMover() == null) {
					((MoverGravityJump)getMover()).setXSpeed(0);
				} else {
					((MoverGravityJump)getMover()).setXSpeed(c.obj2.getMover().getXSpeed());
				}
				break;
			case TOP:
				setyPos(c.obj2.getyPos() - getHeight());
				if (c.obj2.getMover() != null) {
					//make the player move with obj2
					((MoverGravityJump) getMover()).addMovement(c.obj2.getMover().getXSpeed(), c.obj2.getMover().getYSpeed());
				}
				break;
			default:
				break;
			
			}
			break;
		case DEATH:
			die();
			break;
		case INPUT:
			ScriptManager.loadScript(inputScript);
			ScriptManager.bindArgument("player", this);
			ScriptManager.bindArgument("input", ((InputEvent)e).input);
			ScriptManager.executeScript();
			
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
		*/
	}
	
}
