package warpedrealities.bloodgun.actors.shotHandler;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.scenes.World;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.shared.Vec2f;

public interface ShotHandler {

	
	void handleShot(Vec2f position, Vec2f origin,SpriteManager manager, World world);
	
	
}
