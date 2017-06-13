package warpedrealities.bloodgun.actors.impl;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.animators.Animator;
import warpedrealities.bloodgun.actors.movement.HumanoidMovement;
import warpedrealities.bloodgun.actors.shotHandler.ShotHandler;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.shared.Vec2f;

public abstract class Humanoid implements Actor {

	public enum AnimationState {
		NONE, ATTACKING, DYING
	};

	protected Vec2f position;
	protected HumanoidMovement movementHandler;
	protected Square_Int spriteBody;
	protected Animator bodyAnimator;
	protected CollisionHandler collisionHandler;
	protected AnimationState animationState;
	private ShotHandler shotHandler;
	
	public Humanoid() {
		animationState = AnimationState.NONE;
	}

	@Override
	public void update(float DT) {
		movementHandler.handleCollision(position, collisionHandler);
		movementHandler.update(position, DT);

		this.bodyAnimator.animate(DT, movementHandler.getVelocity(), !movementHandler.isFalling(),
				movementHandler.getState(), animationState);
		this.spriteBody.reposition(position);

	}

	public AnimationState getAnimationState() {
		return animationState;
	}

	public void setAnimationState(AnimationState animationState) {
		this.animationState = animationState;
	}

	public CollisionHandler getCollisionHandler() {
		return collisionHandler;
	}

	
}
