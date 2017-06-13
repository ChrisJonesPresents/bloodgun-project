package warpedrealities.bloodgun.movementqualities;

public class PlayerQualities implements MovementQualities {

	private final static float GRAVITY = 8.0F;
	private final static float TERMINALVELOCITY = 8.0F;
	private final static float WALKACCELERATION = 8.0F;
	private final static float MAXWALKSPEED = 4.0F;

	@Override
	public float getMaxWalkSpeed() {

		return MAXWALKSPEED;
	}

	@Override
	public float getAcceleration() {

		return WALKACCELERATION;
	}

	@Override
	public float getGravity() {

		return GRAVITY;
	}

	@Override
	public float getTerminalVelocity() {

		return TERMINALVELOCITY;
	}

}
