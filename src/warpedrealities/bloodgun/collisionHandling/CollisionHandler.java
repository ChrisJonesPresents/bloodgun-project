package warpedrealities.bloodgun.collisionHandling;

import warpedrealities.bloodgun.level.Tile;
import warpedrealities.core.shared.Vec2f;

public interface CollisionHandler {

	public Tile.Collision getWorldCollision(float x, float y);

	public ComplexCollision getLineIntersect(float xStart, float yStart, float xEnd, float yEnd, boolean ignoreThin);

}
