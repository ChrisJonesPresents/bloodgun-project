package warpedrealities.bloodgun.movementqualities;

public class NinjaQualities implements MovementQualities {

	private final static float GRAVITY = 8.0F;
	private final static float TERMINALVELOCITY = 8.0F;
	private final static float WALKACCELERATION = 12.0F;
	private final static float MAXWALKSPEED = 6.0F;

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