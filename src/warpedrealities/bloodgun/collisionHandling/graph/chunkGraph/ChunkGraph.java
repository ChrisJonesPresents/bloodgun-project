package warpedrealities.bloodgun.collisionHandling.graph.chunkGraph;

import java.util.List;

import warpedrealities.bloodgun.collisionHandling.ComplexCollision;
import warpedrealities.bloodgun.collisionHandling.graph.BoundBox;
import warpedrealities.bloodgun.collisionHandling.graph.Line;
import warpedrealities.bloodgun.level.LevelChunk;
import warpedrealities.core.shared.Vec2f;

public interface ChunkGraph {

	void generate(LevelChunk chunk);

	ComplexCollision checkCollision(float xStart, float yStart, float xEnd, float yEnd, boolean ignoreThin,
			BoundBox box);

	List<Line> getLines();
}
