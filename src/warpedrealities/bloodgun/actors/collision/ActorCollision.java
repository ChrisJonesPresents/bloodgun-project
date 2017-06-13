package warpedrealities.bloodgun.actors.collision;

import warpedrealities.bloodgun.collisionHandling.ComplexCollision;
import warpedrealities.bloodgun.collisionHandling.graph.BoundBox;

public interface ActorCollision {

	ComplexCollision check(float xStart, float yStart, float xEnd, float yEnd, BoundBox box1);

}
