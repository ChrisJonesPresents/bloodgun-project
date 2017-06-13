package warpedrealities.bloodgun.collisionHandling.graph;

import warpedrealities.core.shared.Vec2f;

public interface CollisionGraph {

	Vec2f trackIntersection(float xStart, float yStart, float xEnd, float yEnd);

}
