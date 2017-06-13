package warpedrealities.bloodgun.actors.animators;

import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.movement.CommonMovement.State;
import warpedrealities.core.input.MouseHook;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.shared.Vec2f;

public class Player_Arm_Animator implements SimpleAnimator {

	private Square_Int armSprite;
	private float clock;
	private int frameNum;

	public Player_Arm_Animator(Square_Int spriteArm) {
		frameNum = 0;
		this.armSprite = spriteArm;
		clock = 0;
	}

	private void setFrame(int i) {
		if (i != frameNum) {
			armSprite.setImage(i);
			frameNum = i;
		}
	}

	@Override
	public void animate(float dT) {
		// TODO Auto-generated method stub
		if (MouseHook.getInstance().click()) {
			clock += dT;
			if (clock > 0.1F) {
				if (frameNum >= 3) {
					frameNum = 0;
				}
				setFrame(frameNum + 1);
				clock = 0;

			}
		} else {
			setFrame(0);
		}
	}

	@Override
	public void setReversed(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReversed() {
		// TODO Auto-generated method stub
		return false;
	}

}
