package warpedrealities.bloodgun.actors.animators;

import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.movement.CommonMovement.State;
import warpedrealities.core.shared.Vec2f;

public interface SimpleAnimator {

	void animate(float dT);

	void setReversed(boolean b);

	boolean isReversed();
}
