package warpedrealities.bloodgun.actors.movement;

import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.bloodgun.movementqualities.MovementQualities;
import warpedrealities.core.shared.Vec2f;

public class HumanoidMovement extends CommonMovement {





	
	public HumanoidMovement(MovementQualities qualities) {
		actionClock = 0;
		state = State.FALLING;
		moving = false;
		velocity = new Vec2f(0, 0);
		this.qualities = qualities;
	}



	public void drop(Vec2f position, CollisionHandler collisionHandler) {
		if (state == State.SUPPORTED) {
			float y = position.y - 0.5F;
			Collision collision = collisionHandler.getWorldCollision(position.x, y);
			if (collision == Collision.PLATFORM) {
				actionClock = 0.5F;
				state = State.DROPPING;
			}
		}
	}

	public void jump() {
		if (state == State.SUPPORTED) {
			state = State.FALLING;
			velocity.y = 8;
		}
	}

	public Vec2f moveLeft(Vec2f p, float dt) {
		moving = true;
		if (velocity.x > 0 && state == State.SUPPORTED) {
			velocity.x = 0;
		}

		if (velocity.x > qualities.getMaxWalkSpeed() * -1) {
			if (state == State.SUPPORTED) {
				velocity.x -= qualities.getAcceleration() * dt;
			} else {
				velocity.x -= qualities.getAcceleration() * 0.25F * dt;
			}
		}

		return p;

	}

	public Vec2f moveRight(Vec2f p, float dt) {
		moving = true;
		if (velocity.x < 0 && state == State.SUPPORTED) {
			velocity.x = 0;
		}
		if (velocity.x < qualities.getMaxWalkSpeed()) {
			if (state == State.SUPPORTED) {
				velocity.x += qualities.getAcceleration() * dt;
			} else {
				velocity.x += qualities.getAcceleration() * 0.25F * dt;
			}
		}

		return p;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isMoving() {
		return moving;
	}



	public void attack() {
		actionClock = 0;
		state = State.ATTACKING;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
