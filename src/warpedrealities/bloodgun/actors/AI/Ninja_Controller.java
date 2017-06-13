package warpedrealities.bloodgun.actors.AI;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.NPC;
import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.impl.Player;
import warpedrealities.bloodgun.actors.movement.HumanoidMovement;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.core.shared.Vec2f;

public class Ninja_Controller implements Controller {

	private Player target;
	private NPC controlledNPC;

	public Ninja_Controller() {

	}

	@Override
	public void setTarget(Player target) {
		this.target = target;
	}

	@Override
	public void setControlledNPC(NPC npc) {
		this.controlledNPC = npc;
	}

	@Override
	public void update(float dt, CollisionHandler collisionHandler, HumanoidMovement handler, Vec2f position) {

		if (controlledNPC.getAnimationState() == AnimationState.NONE && target.isActive()) {

			if (target.getPosition().x < position.x - 0.5F) {
				handleLeft(handler, position, collisionHandler, dt);
			} else if (target.getPosition().x > position.x + 0.5F) {
				handleRight(handler, position, collisionHandler, dt);
			}
		}

	}

	public void handleLeft(HumanoidMovement handler, Vec2f position, CollisionHandler collision, float dt) {
		handler.moveLeft(position, dt);
		if (target.getPosition().getDistance(position) < 1 && target.getPosition().x < position.x) {
			controlledNPC.setAnimationState(AnimationState.ATTACKING);
			target.kill(position);
		}
		byte targetHeight = getTargetHeight(position, target.getPosition());

		if (collision.getWorldCollision(position.x - 1.0F, position.y) == Collision.SOLID) {
			handler.jump();
		}
		if (targetHeight == 1) {
			handler.jump();
		}
		if (targetHeight == 0 && collision.getWorldCollision(position.x - 0.5F, position.y - 0.5F) == Collision.EMPTY) {
			handler.jump();
		}
		if (targetHeight == -1 && !handler.isFalling()) {
			handler.drop(position, collision);
		}
	}

	public void handleRight(HumanoidMovement handler, Vec2f position, CollisionHandler collision, float dt) {
		handler.moveRight(position, dt);
		if (target.getPosition().getDistance(position) < 1 && target.getPosition().x > position.x) {
			controlledNPC.setAnimationState(AnimationState.ATTACKING);
			target.kill(position);
		}
		byte targetHeight = getTargetHeight(position, target.getPosition());
		if (collision.getWorldCollision(position.x + 1.0F, position.y) == Collision.SOLID) {
			handler.jump();
		}
		if (targetHeight == 1) {
			handler.jump();
		}
		if (targetHeight == 0 && collision.getWorldCollision(position.x + 0.5F, position.y - 0.5F) == Collision.EMPTY) {
			handler.jump();
		}
		if (targetHeight == -1 && !handler.isFalling()) {
			handler.drop(position, collision);
		}
	}

	public byte getTargetHeight(Vec2f position, Vec2f target) {
		float x = Math.abs(position.x - target.x);
		float y = Math.abs(position.y - target.y);
		if (y > x) {
			if (position.y < target.y) {
				return 1;
			} else {
				return -1;
			}
		}
		return 0;
	}
}
