package warpedrealities.bloodgun.scenes.gibs.impl;

import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.level.Tile;
import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.bloodgun.scenes.gibs.Gib;
import warpedrealities.core.shared.Vec2f;

public class GibCollisionHandler {

	private CollisionHandler collisionHandler;
	
	public GibCollisionHandler(CollisionHandler collisionHandler) {
		this.collisionHandler=collisionHandler;
	}
	
	public void checkGib(Gib gib)
	{
		Tile.Collision collision=collisionHandler.getWorldCollision(gib.getPosition().x, gib.getPosition().y);
		if (collision!=Collision.EMPTY)
		{
			gib.setVelocity(new Vec2f(0,0));
			
		}
	}
	

}
