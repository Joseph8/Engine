package csc481;

import csc481.objects.Player;
import csc481.objects.Rectangle;
import processing.core.*;

public class ProcessingSketch extends PApplet {
	Rectangle[] rectangles = new Rectangle[12];
	Player player1;
	int colors[] = new int[6];
	Rectangle ground;
	/**Params to use in collision() that give top/bottom bounds
	of rect positions before move().
	{rect1Left, rect1Right, rect2Left, rect2Right}*/
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
		for (int i = 0; i < rectangles.length; i++) {
			rectangles[i] = new Rectangle(this, random(1, width), random(1,
					height), random(width / 30, width / 10), random(
					height / 30, height / 10), 0);
			float xSpeed = random(-1, 1);
			float ySpeed = sqrt(1 - xSpeed * xSpeed);
			if (random(0, 2) < 1)
				ySpeed *= -1;
			rectangles[i].setXSpeed(xSpeed);
			rectangles[i].setYSpeed(ySpeed);
			rectangles[i].setColor(colors[i % colors.length]);
		}
		ground = new Rectangle(this, 0, 19 * (height / 20), width,
				(height / 20), 0);
		ground.setColor(color(85, 107, 47));
		player1 = new Player(this, (float) width / 2, (float) height / 20,
				(float) width / 30, (float) height / 30, 0);
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
		prevXBounds[0] = player1.getxPos();
		prevXBounds[1] = player1.getxPos() + player1.getWidth();
		player1.move();
		player1.setOnGround(false);
		for (int i = 0; i < rectangles.length; i++) {
			prevXBounds[2] = rectangles[i].getxPos();
			prevXBounds[3] = rectangles[i].getxPos() + rectangles[i].getWidth();
			rectangles[i].move();
			collision(player1, rectangles[i]);
		}
		prevXBounds[2] = ground.getxPos();
		prevXBounds[3] = ground.getxPos() + ground.getWidth();
		collision(player1, ground);

		player1.display();
		for (int i = 0; i < rectangles.length; i++) {
			rectangles[i].display();
		}
		ground.display();
	}

	/**
	 * If a collision is detected between the two rectangles the player
	 * rectangle is moved to the collision boundary.
	 * 
	 * @param rect1nplayer rectangle
	 * @param rect2 other rectangle
	 */
	private void collision(Rectangle rect1, Rectangle rect2) {
		float rect1Left = rect1.getxPos();
		float rect1Right = rect1.getxPos() + rect1.getWidth();
		float rect2Left = rect2.getxPos();
		float rect2Right = rect2.getxPos() + rect2.getWidth();
		float rect1Top = rect1.getyPos();
		float rect1Bottom = rect1.getyPos() + rect1.getHeight();
		float rect2Top = rect2.getyPos();
		float rect2Bottom = rect2.getyPos() + rect2.getHeight();

		if ((rect1Bottom > rect2Top && rect1Bottom < rect2Bottom)) {
			if ((rect1Right > rect2Left && rect1Right < rect2Right)
					|| (rect1Left < rect2Right && rect1Left > rect2Left)) {
				//if last turn rect1's x bounds were inside of rect2
				if ((prevXBounds[1] > prevXBounds[2]) && (prevXBounds[0] < prevXBounds[3])) { //! DOES NOT CURRENTLY WORK (use XOR here?)
					//then we know rect1 is colliding with the top of rect2
					rect1.setyPos(rect2Top - rect1.getHeight());
					if (rect1 instanceof Player) {
						((Player)rect1).setOnGround(true);
						//make the player move with rect2
						((Player)rect1).addMovement(rect2.getXSpeed(), rect2.getYSpeed());
						return;
					}					
				} else {
					//if last turn rect1's right is less than rect 2's left
					if (prevXBounds[1] <= prevXBounds[2]) {
						//then we know rect1 is colliding with the left of rect2 
						rect1.setxPos(rect2Left - rect1.getWidth());
					} else {
						//if last turn rect1's left is greater than rect 2's right
						//then we know rect1 is colliding with the right of rect2
						rect1.setxPos(rect2Right);						
					}
				}
				if (rect1 instanceof Player) {
					((Player)rect1).setOnGround(true);
				}				
				//rect1.setColor(color(255, 0, 0));
				//rect2.setColor(color(255, 0, 0));
				return;
			}
		} else if (rect1Top < rect2Bottom && rect1Top > rect2Top) {
			if ((rect1Right > rect2Left && rect1Right < rect2Right)
					|| (rect1Left < rect2Right && rect1Left > rect2Left)) {
				//if last turn rect1's x bounds were inside of rect2
				if ((prevXBounds[1] > prevXBounds[2]) && (prevXBounds[0] < prevXBounds[3])) { //! DOES NOT CURRENTLY WORK
					//then we know rect1 is colliding with the bottom of rect2
					rect1.setyPos(rect2Bottom);
					if (rect1 instanceof Player) {
						((Player)rect1).setOnGround(true);
						return;
					}					
				} else {
					//if last turn rect1's right is less than rect 2's left
					if (prevXBounds[1] <= prevXBounds[2]) {
						//then we know rect1 is colliding with the left of rect2 
						rect1.setxPos(rect2Left - rect1.getWidth());
					} else {
						//if last turn rect1's left is greater than rect 2's right
						//then we know rect1 is colliding with the right of rect2
						rect1.setxPos(rect2Right);						
					}
				}
				if (rect1 instanceof Player) {
					((Player)rect1).setOnGround(true);
				}				
				//rect1.setColor(color(255, 0, 0));
				//rect2.setColor(color(255, 0, 0));
				return;
			}
		}
		//rect1.setOnGround(false);
		//rect1.setColor(color(0, 0, 0));

	}

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

	// public void keyPressed() {
	// System.out.println("HERE 1");
	// if (key == 'a') {
	// player1.moveLeft();
	// } else if (key == 'd') {
	// player1.moveRight();
	// }
	// }

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "ProcessingSketch" });
	}
}
