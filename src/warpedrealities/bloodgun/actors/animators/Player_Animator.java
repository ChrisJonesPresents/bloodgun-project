package warpedrealities.bloodgun.actors.animators;

import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.movement.HumanoidMovement;
import warpedrealities.bloodgun.actors.movement.CommonMovement.State;
import warpedrealities.core.rendering.Sprite;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.shared.Vec2f;

public class Player_Animator implements Animator {

	private Square_Int bodySprite;
	private float clock;
	private int frameNum;
	private boolean reversed;

	public Player_Animator(Square_Int spriteBody) {
		frameNum = 0;
		this.bodySprite = spriteBody;
	}

	private void setFrame(int frame) {
		if (frame != frameNum) {
			bodySprite.setImage(frame);
			frameNum = frame;
		}
	}

	@Override
	public void setReversed(boolean reversed) {
		if (this.reversed != reversed) {
			bodySprite.setFlipped(reversed);
		}
		this.reversed = reversed;

	}

	private void walkCycle(float dt, Vec2f velocity) {
		boolean backwards = false;
		if (velocity.x < 0) {
			backwards = true;
		}
		if (this.reversed) {
			backwards = !backwards;
		}

		if (!backwards) {
			clock += dt * 8;
			if (clock > 8) {
				clock = 0;
			}

		} else {
			clock -= dt * 4;
			if (clock < 0) {
				clock = 8;
			}
		}

		setFrame((int) clock + 4);
	}

	private void dying(float dt) {
		if (clock > 0) {
			clock = 0;
		}
		if (clock > -3) {
			clock -= dt * 8;
			setFrame((int) (12 + (clock * -1)));
		}

	}

	@Override
	public void animate(float dt, Vec2f velocity, boolean supported, HumanoidMovement.State state,
			AnimationState animationState) {
		if (animationState == AnimationState.DYING) {
			dying(dt);
		} else {
			if (supported) {
				if (velocity.x == 0) {
					setFrame(0);
				} else {
					walkCycle(dt, velocity);
				}
			} else {
				if (velocity.y > 0) {
					setFrame(1);
				} else {
					setFrame(2);
				}
			}
		}

	}

	@Override
	public boolean isReversed() {
		return reversed;
	}
}
