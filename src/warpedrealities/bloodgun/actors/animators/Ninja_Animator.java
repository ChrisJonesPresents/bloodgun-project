package warpedrealities.bloodgun.actors.animators;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.movement.CommonMovement;
import warpedrealities.bloodgun.actors.movement.HumanoidMovement;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.shared.Vec2f;

public class Ninja_Animator implements Animator {

	private Square_Int bodySprite;
	private Square_Int swordSprite;
	private float clock;
	private int frameNum;
	private int swordFrameNum;
	private boolean reversed;
	private Actor actor;

	public Ninja_Animator(Actor actor, Square_Int spriteBody, Square_Int spriteSword) {
		this.actor = actor;
		swordFrameNum = 0;
		frameNum = 0;
		this.bodySprite = spriteBody;
		this.swordSprite = spriteSword;
	}

	private void walkCycle(float dt) {
		clock += dt * 16;
		if (clock > 4) {
			clock -= 4;
		}
		setFrame((int) clock);
	}

	private void setFrame(int frame) {
		if (frame != frameNum) {
			bodySprite.setImage(frame);
			frameNum = frame;
		}
	}

	private void setSwordFrame(int frame) {
		if (frame != swordFrameNum) {
			swordSprite.setImage(frame);
			swordFrameNum = frame;
		}
	}

	private void setFlipped(Vec2f velocity) {
		if (velocity.x > 0) {
			bodySprite.setFlipped(true);
			swordSprite.setFlipped(true);
			reversed = true;
		} else if (velocity.x < 0) {
			bodySprite.setFlipped(false);
			swordSprite.setFlipped(false);
			reversed = false;
		}
	}

	private void falling(Vec2f velocity) {
		if (velocity.y > 0) {
			setFrame(4);
		} else {
			setFrame(5);
		}
	}

	private void attack(float dt) {
		swordSprite.setVisible(true);
		if (clock > 0) {
			clock = 0;
		}
		clock -= dt * 16;
		if (clock < -4) {
			actor.setAnimationState(AnimationState.NONE);
			swordSprite.setVisible(false);
		}
		setFrame((int) (clock * -1) + 8);
		setSwordFrame((int) (clock * -1) + 12);
	}

	@Override
	public void animate(float dt, Vec2f velocity, boolean supported, CommonMovement.State state,
			AnimationState animationState) {
		if (animationState == animationState.ATTACKING) {
			attack(dt);

		} else {

			setFlipped(velocity);

			if (supported) {
				walkCycle(dt);
			} else {
				falling(velocity);
			}
		}

	}

	@Override
	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	public boolean isReversed() {
		return reversed;
	}

}
