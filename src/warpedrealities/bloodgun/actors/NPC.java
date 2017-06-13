package warpedrealities.bloodgun.actors;

import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.shotHandler.ShotHandler;

public interface NPC extends Actor {

	void activate();

	void setPosition(float x, float f);
	
	void removeSprites();

	ShotHandler getShotHandler();
}
