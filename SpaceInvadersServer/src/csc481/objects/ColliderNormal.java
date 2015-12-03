package csc481.objects;

import java.io.Serializable;

import csc481.ProcessingSketch;
import csc481.eventhandlers.EventManager;
import csc481.events.CollisionEdge;
import csc481.events.CollisionEvent;
import csc481.events.DeathEvent;

/**
 * Collides objects that have a medium collision priority (they don't 
 * move high collision priority but move low collision priority)
 * @author Joseph Gregory
 *
 */
public class ColliderNormal extends Collider implements Serializable {
	private static final long serialVersionUID = 5354055632444588055L;

	public ColliderNormal(GameObject obj) {
		owner = obj;
	}
	
	@Override
	public void collide(GameObject obj2) {
		if (obj2 instanceof SpawnPoint) return;
		if (obj2 instanceof DeathZone) {
			collideDeathZone(obj2);
			return;
		}
		float obj1Left = owner.xPos;
		float obj1Right = owner.xPos + owner.width;
		float obj2Left = obj2.getxPos();
		float obj2Right = obj2.getxPos() + obj2.getWidth();
		float obj1Top = owner.yPos;
		float obj1Bottom = owner.yPos + owner.height;
		float obj2Top = obj2.getyPos();
		float obj2Bottom = obj2.getyPos() + obj2.getHeight();

		if ((obj1Bottom > obj2Top && obj1Bottom < obj2Bottom)) {
			if ((obj1Right > obj2Left && obj1Right < obj2Right)
					|| (obj1Left < obj2Right && obj1Left > obj2Left)) {
				//if last turn obj1's x bounds were inside of obj2
				
				if ((owner.getPrevXBoundRight() > obj2.getPrevXBoundLeft()) && (owner.getPrevXBoundLeft() < obj2.getPrevXBoundRight())) { //! DOES NOT CURRENTLY WORK (use XOR here?)
					//then we know obj1 is colliding with the top of obj2
					ProcessingSketch.getEventManager().raise(new CollisionEvent(owner, obj2, CollisionEdge.TOP, ProcessingSketch.getGameTimeline().getIterations()));
//					owner.setyPos(obj2Top - owner.getHeight());
//					if (owner.getMover() != null && owner.getMover() instanceof MoverGravityJump) {
//						if (owner instanceof Player) ((Player)owner).setOnGround(true); //! CHANGE- caller shouldn't be a Player
//						//make the player move with obj2
//						if (obj2.getMover() != null) {
//							((MoverGravityJump) owner.getMover()).addMovement(obj2.getMover().getXSpeed(), obj2.getMover().getYSpeed());
//						}
//					}
					return;
				} else {
					//if last turn obj1's right is less than obj2's left
					if (owner.getPrevXBoundRight() <= obj2.getPrevXBoundLeft()) {
						//then we know obj1 is colliding with the left of obj2 
						ProcessingSketch.getEventManager().raise(new CollisionEvent(owner, obj2, CollisionEdge.LEFT, ProcessingSketch.getGameTimeline().getIterations()));
//						owner.setxPos(obj2Left - owner.getWidth());
					} else {
						//if last turn obj1's left is greater than obj2's right
						//then we know obj1 is colliding with the right of obj2
						ProcessingSketch.getEventManager().raise(new CollisionEvent(owner, obj2, CollisionEdge.RIGHT, ProcessingSketch.getGameTimeline().getIterations()));
//						owner.setxPos(obj2Right);						
					}
//					if (owner.getMover() != null && owner.getMover() instanceof MoverGravityJump) {
// 						if (obj2.getMover() == null) {
// 							((MoverGravityJump)owner.getMover()).setXSpeed(0);
// 						} else {
// 							((MoverGravityJump)owner.getMover()).setXSpeed(obj2.getMover().getXSpeed());
// 						}
//					}
				}
//				if (owner instanceof Player) {
//					((Player)owner).setOnGround(true);
//				}				
				//obj1.setColor(color(255, 0, 0));
				//obj2.setColor(color(255, 0, 0));
				return;
			}
		} else if (obj1Top < obj2Bottom && obj1Top > obj2Top) {
			if ((obj1Right > obj2Left && obj1Right < obj2Right)
					|| (obj1Left < obj2Right && obj1Left > obj2Left)) {
				//if last turn obj1's x bounds were inside of obj2
				if ((owner.getPrevXBoundRight() > obj2.getPrevXBoundLeft()) && (owner.getPrevXBoundLeft() < obj2.getPrevXBoundRight())) { //! DOES NOT CURRENTLY WORK
					//then we know obj1 is colliding with the bottom of obj2
					ProcessingSketch.getEventManager().raise(new CollisionEvent(owner, obj2, CollisionEdge.BOTTOM, ProcessingSketch.getGameTimeline().getIterations()));
//					owner.setyPos(obj2Bottom);
//					if (owner instanceof Player) {
//						((Player)owner).setOnGround(true);
//						return;
//					}
//					if (owner.getMover() != null && owner.getMover() instanceof MoverGravityJump) {
// 						if (obj2.getMover() == null) {
// 							((MoverGravityJump)owner.getMover()).setYSpeed(0);
// 						} else {
// 							((MoverGravityJump)owner.getMover()).setYSpeed(obj2.getMover().getYSpeed());
// 						}
//					}
				} else {
					//if last turn obj1's right is less than obj2's left
					if (owner.getPrevXBoundRight() <= obj2.getPrevXBoundLeft()) {
						//then we know obj1 is colliding with the left of obj2 
						ProcessingSketch.getEventManager().raise(new CollisionEvent(owner, obj2, CollisionEdge.LEFT, ProcessingSketch.getGameTimeline().getIterations()));
//						owner.setxPos(obj2Left - owner.getWidth());
					} else {
						//if last turn obj1's left is greater than obj2's right
						//then we know obj1 is colliding with the right of obj2
						ProcessingSketch.getEventManager().raise(new CollisionEvent(owner, obj2, CollisionEdge.RIGHT, ProcessingSketch.getGameTimeline().getIterations()));
//						owner.setxPos(obj2Right);						
					}
//					if (owner.getMover() != null && owner.getMover() instanceof MoverGravityJump) {
// 						if (obj2.getMover() == null) {
// 							((MoverGravityJump)owner.getMover()).setXSpeed(0);
// 						} else {
// 							((MoverGravityJump)owner.getMover()).setXSpeed(obj2.getMover().getXSpeed());
// 						}
//					}
				}
//				if (owner instanceof Player) {
//					((Player)owner).setOnGround(true);
//				}				
				//obj1.setColor(color(255, 0, 0));
				//obj2.setColor(color(255, 0, 0));
				return;
			}
		}
		//obj1.setOnGround(false);
		//obj1.setColor(color(0, 0, 0));
		
	}

	private void collideDeathZone(GameObject obj2) {
		float obj1Left = owner.xPos;
		float obj1Right = owner.xPos + owner.width;
		float obj2Left = obj2.getxPos();
		float obj2Right = obj2.getxPos() + obj2.getWidth();
		float obj1Top = owner.yPos;
		float obj1Bottom = owner.yPos + owner.height;
		float obj2Top = obj2.getyPos();
		float obj2Bottom = obj2.getyPos() + obj2.getHeight();

		if ((obj1Bottom > obj2Top && obj1Bottom < obj2Bottom) || 
				(obj1Top < obj2Bottom && obj1Top > obj2Top)) {
			if ((obj1Right > obj2Left && obj1Right < obj2Right)
					|| (obj1Left < obj2Right && obj1Left > obj2Left)) {
				ProcessingSketch.getEventManager().raise(new DeathEvent(ProcessingSketch.getGameTimeline().getIterations()));
			}
		}
	}
}
