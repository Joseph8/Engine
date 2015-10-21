package csc481;

import java.util.LinkedList;

import csc481.objects.DeathZone;
import csc481.objects.GameObject;
import csc481.objects.MoverGravityJump;
import csc481.objects.MoverNoGravity;
import csc481.objects.Player;
import csc481.objects.MovingPlatform;
import csc481.objects.SpawnPoint;
import csc481.objects.StaticPlatform;
import processing.core.*;

public class ProcessingSketch extends PApplet {
	MovingPlatform[] rectangles = new MovingPlatform[12];
	Player player1;
	int colors[] = new int[6];
	MovingPlatform ground;
	private static LinkedList<GameObject> objects;
	/**Params to use in collision() that give top/bottom bounds
	of rect positions before move().
	{obj1Left, obj1Right, obj2Left, obj2Right}*/
	float prevXBounds[] = new float[4];

	public void setup() {
		colors[0] = color(238, 233, 233);
		colors[1] = color(205, 192, 176);
		colors[2] = color(135, 206, 250);
		colors[3] = color(154, 205, 50);
		colors[4] = color(255, 99, 71);
		colors[5] = color(255, 215, 0);
		size(800, 800);
		background(100);
		objects = new LinkedList<GameObject>();
		
		player1 = new Player(this, (float) width / 2, (float) height / 20,
				(float) width / 30, (float) height / 30, 0);
		objects.add(player1);
		for (int i = 0; i < rectangles.length; i++) {
			float xSpeed = random(-1, 1);
			float ySpeed = sqrt(1 - xSpeed * xSpeed);
			if (random(0, 2) < 1)
				ySpeed *= -1;
			objects.add( new MovingPlatform(this, random(1, width), random(1,
					height), random(width / 30, width / 10), random(
					height / 30, height / 10), 0, colors[i % colors.length], xSpeed, ySpeed));
			
		}
		//objects.add( new StaticPlatform(this, 0, 19 * (height / 20), width, (height / 20), 0, color(85, 107, 47))); // ground
		objects.add( new StaticPlatform(this, 0, 19 * (height / 20), width/10,
				(height / 20), 0, color(85, 107, 47)));
		objects.add( new StaticPlatform(this, width/2, 15 * (height / 20), width/10,
				(height / 20), 0, color(85, 107, 47)));
		objects.add( new StaticPlatform(this, width/3, 8 * (height / 20), width/10,
				(height / 20), 0, color(85, 107, 47)));
		objects.add( new DeathZone(this, 0, 24 * (height / 25), width, (height / 25), 0));
		objects.add( new SpawnPoint(this, 5, 17 * (height / 20)));
		objects.add( new SpawnPoint(this, width/2 + 5, 13 * (height / 20)));
		objects.add( new SpawnPoint(this, width/3 + 5, 6 * (height / 20)));
		
	}

	public void draw() {
		// stroke(255);
		// if (mousePressed) {
		// line(mouseX, mouseY, pmouseX, pmouseY);
		// }
		// System.out.println("HERE 1");
		// if (keyPressed) {
		// switch(key) {
		// case 'a':
		// player1.moveLeft();
		// break;
		// case 'd':
		// player1.moveRight();
		// break;
		// case ' ':
		// player1.jump();
		// break;
		// }
		// }
		background(100);
//		prevXBounds[0] = player1.getxPos();
//		prevXBounds[1] = player1.getxPos() + player1.getWidth();
		
		//PRIORITY COLLLISION
		/**
		//collide lowest to highest priority
		boolean firstForPriority = true;
		for (int curPri = 0; curPri < 3; curPri++) {
			firstForPriority = true;
			//process the current priority
			for (GameObject object1 : objects) {
				int priority = object1.getCollider().getPriority();
				if (priority != curPri) continue;
				
				if (firstForPriority) {
					//equalCollide with same priority objects
					for (GameObject object2 : objects) {
						if (priority == object2.getCollider().getPriority()) {
							
							
						}
					}
					firstForPriority = false;
				}
				//wallCollide with higher priority objects
				for (GameObject object2 : objects) { 
					if (priority < object2.getCollider().getPriority()) {
						object1.getCollider().wallCollide(object2);
					}
				}
			}
		}
		*/
		
		//move and collide
		player1.getCollider().setPrevOwnerXBounds();
		player1.getMover().move();
		player1.setOnGround(false);
		int i = 0;
		for (GameObject obj : objects) {
			if (i == 0) {
				i = 1;
				continue;
			}
			player1.getCollider().setPrevObj2XBounds(obj);
			if (obj.getMover() != null) obj.getMover().move();
			player1.getCollider().collide(obj);
		}
		/**
		player1.getMover().move();
		player1.setOnGround(false);
		for (int i = 0; i < rectangles.length; i++) {
//			prevXBounds[2] = rectangles[i].getxPos();
//			prevXBounds[3] = rectangles[i].getxPos() + rectangles[i].getWidth();
			player1.getCollider().setPrevObj2XBounds(rectangles[i]);
			rectangles[i].getMover().move();
			player1.getCollider().collide(rectangles[i]);
		}
		//prevXBounds[2] = ground.getxPos();
		//prevXBounds[3] = ground.getxPos() + ground.getWidth();
		player1.getCollider().setPrevObj2XBounds(ground);
		player1.getCollider().collide(ground);
		*/
		
		//render
		for (GameObject obj : objects) {
			if (obj.getRenderer() != null) obj.getRenderer().render();
		}
	}

	/**
	 * If a collision is detected between the two rectangles the player
	 * rectangle is moved to the collision boundary.
	 * 
	 * @param obj1nplayer rectangle
	 * @param obj2 other rectangle
	 */
	/**
	private void collision(GameObject obj1, GameObject obj2) {
		float obj1Left = obj1.getxPos();
		float obj1Right = obj1.getxPos() + obj1.getWidth();
		float obj2Left = obj2.getxPos();
		float obj2Right = obj2.getxPos() + obj2.getWidth();
		float obj1Top = obj1.getyPos();
		float obj1Bottom = obj1.getyPos() + obj1.getHeight();
		float obj2Top = obj2.getyPos();
		float obj2Bottom = obj2.getyPos() + obj2.getHeight();

		if ((obj1Bottom > obj2Top && obj1Bottom < obj2Bottom)) {
			if ((obj1Right > obj2Left && obj1Right < obj2Right)
					|| (obj1Left < obj2Right && obj1Left > obj2Left)) {
				//if last turn obj1's x bounds were inside of obj2
				if ((prevXBounds[1] > prevXBounds[2]) && (prevXBounds[0] < prevXBounds[3])) { //! DOES NOT CURRENTLY WORK (use XOR here?)
					//then we know obj1 is colliding with the top of obj2
					obj1.setyPos(obj2Top - obj1.getHeight());
					if (obj1.getMover() != null && obj1.getMover() instanceof MoverGravityJump) {
						if (obj1 instanceof Player) ((Player)obj1).setOnGround(true); //! CHANGE- caller shouldn't be a Player
						//make the player move with obj2
						((MoverGravityJump) obj1.getMover()).addMovement(obj2.getMover().getXSpeed(), obj2.getMover().getYSpeed());
					}
					return;
				} else {
					//if last turn obj1's right is less than obj2's left
					if (prevXBounds[1] <= prevXBounds[2]) {
						//then we know obj1 is colliding with the left of obj2 
						obj1.setxPos(obj2Left - obj1.getWidth());
					} else {
						//if last turn obj1's left is greater than obj2's right
						//then we know obj1 is colliding with the right of obj2
						obj1.setxPos(obj2Right);						
					}
					if (obj1.getMover() != null && obj1.getMover() instanceof MoverGravityJump) ((MoverGravityJump)obj1.getMover()).setXSpeed(obj2.getMover().getXSpeed());
				}
				if (obj1 instanceof Player) {
					((Player)obj1).setOnGround(true);
				}				
				//obj1.setColor(color(255, 0, 0));
				//obj2.setColor(color(255, 0, 0));
				return;
			}
		} else if (obj1Top < obj2Bottom && obj1Top > obj2Top) {
			if ((obj1Right > obj2Left && obj1Right < obj2Right)
					|| (obj1Left < obj2Right && obj1Left > obj2Left)) {
				//if last turn obj1's x bounds were inside of obj2
				if ((prevXBounds[1] > prevXBounds[2]) && (prevXBounds[0] < prevXBounds[3])) { //! DOES NOT CURRENTLY WORK
					//then we know obj1 is colliding with the bottom of obj2
					obj1.setyPos(obj2Bottom);
					if (obj1 instanceof Player) {
						((Player)obj1).setOnGround(true);
						return;
					}
					if (obj1.getMover() != null && obj1.getMover() instanceof MoverGravityJump) ((MoverGravityJump)obj1.getMover()).setYSpeed(obj2.getMover().getYSpeed());
				} else {
					//if last turn obj1's right is less than obj2's left
					if (prevXBounds[1] <= prevXBounds[2]) {
						//then we know obj1 is colliding with the left of obj2 
						obj1.setxPos(obj2Left - obj1.getWidth());
					} else {
						//if last turn obj1's left is greater than obj2's right
						//then we know obj1 is colliding with the right of obj2
						obj1.setxPos(obj2Right);						
					}
					if (obj1.getMover() != null && obj1.getMover() instanceof MoverGravityJump) ((MoverGravityJump)obj1.getMover()).setXSpeed(obj2.getMover().getXSpeed());
				}
				if (obj1 instanceof Player) {
					((Player)obj1).setOnGround(true);
				}				
				//obj1.setColor(color(255, 0, 0));
				//obj2.setColor(color(255, 0, 0));
				return;
			}
		}
		//obj1.setOnGround(false);
		//obj1.setColor(color(0, 0, 0));

	}
	*/

	public void keyPressed() {
		switch (key) {
		case 'a':
			player1.moveLeft();
			break;
		case 'd':
			player1.moveRight();
			break;
		case ' ':
			player1.jump();
			break;
		}
	}

	public void keyReleased() {
		switch (key) {
		case 'a':
			player1.stopMoveLeft();
			break;
		case 'd':
			player1.stopMoveRight();
			break;
		case ' ':
			player1.stopJump();
			break;
		}
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "ProcessingSketch" });
	}
	
	public static LinkedList<GameObject> getObjects() {
		return objects;
	}
}
