package warpedrealities.bloodgun.actors.animators;

import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.movement.CommonMovement.State;
import warpedrealities.core.shared.Vec2f;

public interface Animator {

	void animate(float dT, Vec2f velocity, boolean supported, State state, AnimationState animationState);

	void setReversed(boolean b);

	boolean isReversed();


}
