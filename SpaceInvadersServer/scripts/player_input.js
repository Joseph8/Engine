function update() {
	print(type);
	if (type == 'COLLISION') {
		if (isPlayer) {
						
		} 
		player.setOnGround(true);
		if (edge == 'BOTTOM') {
			player.setyPos(c.obj2.getyPos() + c.obj2.getHeight());
			if (c.obj2.getMover() == null) {
				playerMover.setYSpeed(0);
			} else {
				playerMover.setYSpeed(c.obj2.getMover().getYSpeed());
			}
		} else if (edge == 'LEFT') {
			player.setxPos(c.obj2.getxPos() - player.getWidth());
			if (c.obj2.getMover() == null) {
				playerMover.setXSpeed(0);
			} else {
				playerMover.setXSpeed(c.obj2.getMover().getXSpeed());
			} 
		} else if (edge == 'RIGHT') {
			player.setxPos(c.obj2.getxPos() + c.obj2.getWidth());
			if (c.obj2.getMover() == null) {
				playerMover.setXSpeed(0);
			} else {
				playerMover.setXSpeed(c.obj2.getMover().getXSpeed());
			}
		} else if (edge == 'TOP') {
			player.setyPos(c.obj2.getyPos() - player.getHeight());
			if (c.obj2.getMover() != null) {
				//make the player move with obj2
				playerMover.addMovement(c.obj2.getMover().getXSpeed(), c.obj2.getMover().getYSpeed());
			}
		}
	} else if (type == 'DEATH ') {
		player.die();
	} else if (type == 'INPUT') {
		if (input == 'SHOOT') {
				player.shoot();
		} else if (input == 'MOVE_LEFT') {
				player.moveLeft();
		} else if (input == 'MOVE_RIGHT') {
				player.moveRight();
		} else if (input == 'STOP_SHOOT') {
				player.stopShoot();
		} else if (input == 'STOP_MOVE_LEFT') {
				player.stopMoveLeft();
		} else if (input == 'STOP_MOVE_RIGHT') {
				player.stopMoveRight();
		}

	} else if (type == 'NEW_OBJECT') {

	} else if (type == 'SPAWN') {

	}
	print('HERE------');
}
