function update() {
	switch (input) {
		case JUMP:
			player.jump();
			print('jump');
			break;
		case MOVE_LEFT:
			player.moveLeft();
			break;
		case MOVE_RIGHT:
			player.moveRight();
			break;
		case STOP_JUMP:
			player.stopJump();
			break;
		case STOP_MOVE_LEFT:
			player.stopMoveLeft();
			break;
		case STOP_MOVE_RIGHT:
			player.stopMoveRight();
			break;
		default:
			break;
	}


}