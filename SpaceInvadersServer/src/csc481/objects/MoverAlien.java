package csc481.objects;

import csc481.ProcessingSketch;

public class MoverAlien extends Mover {
	private static final long serialVersionUID = 919532994802687313L;
	private static final float numHorizontalMovements = 10;
	private static float spacer;
	private float moveToSideInterval;
	private boolean movingRight;
	/**milliseconds between the alien moving down*/
	private int descendInterval;
	private int numInterval;
	private float descentDepth;
	
	public MoverAlien(int descendInterval, int descentDepth, float spacer, GameObject obj) {
		this.xSpeed = 0;
		this.ySpeed = 0;
		owner = obj;
		this.descendInterval = descendInterval;
		this.descentDepth = descentDepth;
		numInterval = 0;
		this.spacer = spacer;
		moveToSideInterval = spacer/numHorizontalMovements;
		movingRight = true;
	}
	
	public boolean move() {
		//move
		if (ProcessingSketch.getGameTimeline().getGameTime() > descendInterval * numInterval) {
			numInterval++;
			if (numInterval%numHorizontalMovements == 0) {
				owner.yPos += descentDepth;
				movingRight = !movingRight;
			} else {
				if(movingRight) {
					owner.xPos += moveToSideInterval;
				} else {
					owner.xPos -= moveToSideInterval;
				}
			}
			
		}
		if (owner.yPos > ProcessingSketch.getInstance().height - (owner.height*3)) {
			ProcessingSketch.gameOver();
		}
//		if (owner.xPos > ProcessingSketch.getInstance().width || owner.xPos < 0) {
//			xSpeed *=-1;
//		}
		return true;
	}

}
