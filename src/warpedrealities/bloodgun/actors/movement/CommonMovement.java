package warpedrealities.bloodgun.actors.movement;

import warpedrealities.bloodgun.actors.movement.CommonMovement.State;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.bloodgun.movementqualities.MovementQualities;
import warpedrealities.core.shared.Vec2f;

public class CommonMovement {
	public enum State {
		SUPPORTED, FALLING, DROPPING, LEAPING, ATTACKING, DYING
	};
	protected MovementQualities qualities;
	protected Vec2f velocity;
	protected State state;
	protected boolean moving;
	protected float actionClock;
	
	private Vec2f nudgeUpwards(Vec2f position, float y) {
		float yRounded = Math.round(y);
		if (position.y < yRounded + 0.45F) {
			position.y = yRounded + 0.45F;
		}
		return position;
	}

	protected Vec2f handleFloor(Vec2f position, CollisionHandler collisionHandler) {
		// check floor is under us
		float y = position.y - 0.5F;
		Collision collisionLeft = collisionHandler.getWorldCollision(position.x - 0.25F, y);
		Collision collisionRight = collisionHandler.getWorldCollision(position.x + 0.25F, y);
		if (collisionLeft == Collision.EMPTY && collisionRight == Collision.EMPTY) {
			state = State.FALLING;
		}
		if (state != State.SUPPORTED && (collisionLeft == Collision.SOLID || collisionRight == Collision.SOLID)) {
			if (state != State.ATTACKING) {
				state = State.SUPPORTED;
			}
			velocity.y = 0;
			return nudgeUpwards(position, y);
		}
		if (state == State.FALLING && (collisionLeft == Collision.PLATFORM || collisionRight == Collision.PLATFORM)) {
			state = State.SUPPORTED;
			velocity.y = 0;
			return nudgeUpwards(position, y);
		}

		return position;
	}

	protected void handleCeiling(Vec2f position, CollisionHandler collisionHandler) {
		// TODO Auto-generated method stub
		float y = position.y + 0.5F;
		Collision collision = collisionHandler.getWorldCollision(position.x, y);
		if (collision == Collision.SOLID) {
			velocity.y = 0;
		}
	}

	private Vec2f nudgeLeft(Vec2f position, float x) {
		float xRounded = Math.round(x);
		if (position.x > xRounded - 0.45F) {
			position.x = xRounded - 0.45F;
		}

		return position;
	}

	private Vec2f nudgeRight(Vec2f position, float x) {
		float xRounded = Math.round(x);
		if (position.x < xRounded + 0.45F) {
			position.x = xRounded + 0.45F;
		}

		return position;
	}

	protected void handleLeftCollision(Vec2f position, CollisionHandler collisionHandler) {
		// TODO Auto-generated method stub
		float x = position.x - 0.4F;
		float y = position.y - 0.2F;
		Collision collision = collisionHandler.getWorldCollision(x, y);
		if (Collision.SOLID == collision) {
			velocity.x = 0;
			nudgeRight(position, x);
		}
	}

	protected void handleRightCollision(Vec2f position, CollisionHandler collisionHandler) {
		// TODO Auto-generated method stub
		float x = position.x + 0.4F;
		float y = position.y - 0.2F;
		Collision collision = collisionHandler.getWorldCollision(x, y);
		if (Collision.SOLID == collision) {
			velocity.x = 0;
			nudgeLeft(position, x);
		}
	}

	public Vec2f handleCollision(Vec2f position, CollisionHandler collisionHandler) {
		if (moving || isFalling()) {
			if (velocity.y <= 0) {
				handleFloor(position, collisionHandler);
			} else {
				handleCeiling(position, collisionHandler);
			}
			if (velocity.x > 0) {
				handleRightCollision(position, collisionHandler);
			}
			if (velocity.x < 0) {
				handleLeftCollision(position, collisionHandler);
			}

		}
		return position;
	}

	public boolean isFalling() {
		if (State.SUPPORTED == state) {
			return false;
		}
		return true;
	}
	
	public void update(Vec2f position, float dt) {
		if (!moving && state == State.SUPPORTED && velocity.x != 0) {
			velocity.x = 0;
		}

		handleGravity(position, dt);
		position.x += velocity.x * dt;
		moving = false;

	}

	public void handleGravity(Vec2f position, float dt) {
		if (state != State.SUPPORTED) {
			if (state == State.DROPPING) {
				actionClock -= dt;
				if (actionClock < 0) {
					actionClock = 0;
					state = State.FALLING;
				}
			}

			if (velocity.y > -qualities.getTerminalVelocity()) {
				velocity.y -= qualities.getGravity() * dt;
			}
			position.y += velocity.y * dt;

		}
	}

	public Vec2f getVelocity() {
		return velocity;
	}

	public State getState() {
		return state;
	}
}
