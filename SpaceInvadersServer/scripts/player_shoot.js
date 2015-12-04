function update() {
	if (player.onGround) {
		//((MoverGravityJump)player.mover).ySpeed = player.jumpSpeed;
		playerMover.setYSpeed('-3.5');
		player.secondJumpAvailable = true;
		player.isJumping = true;
	} else if (!player.isJumping && player.secondJumpAvailable) {
		//((MoverGravityJump)player.mover).ySpeed = player.jumpSpeed;
		playerMover.setYSpeed('-3.5');
		player.secondJumpAvailable = false;
	}
}