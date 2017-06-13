package warpedrealities.bloodgun.actors;

import warpedrealities.bloodgun.actors.collision.ActorCollision;
import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.shotHandler.ShotHandler;
import warpedrealities.core.rendering.Sprite;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.shared.Vec2f;

public interface Actor {

	Vec2f getPosition();

	void update(float DT);

	boolean isActive();


	void setAnimationState(AnimationState attacking);

	AnimationState getAnimationState();

	ActorCollision getCollision();

	void removeSprites();
	
	boolean removalNeeded();
}
