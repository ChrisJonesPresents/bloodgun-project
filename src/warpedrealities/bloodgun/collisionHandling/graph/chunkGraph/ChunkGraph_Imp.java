package warpedrealities.bloodgun.collisionHandling.graph.chunkGraph;

import java.util.ArrayList;
import java.util.List;

import warpedrealities.bloodgun.collisionHandling.ComplexCollision;
import warpedrealities.bloodgun.collisionHandling.graph.BoundBox;
import warpedrealities.bloodgun.collisionHandling.graph.Line;
import warpedrealities.bloodgun.level.LevelChunk;
import warpedrealities.core.shared.Vec2f;

public class ChunkGraph_Imp implements ChunkGraph {

	private List<Line> lines;

	public ChunkGraph_Imp() {
		lines = new ArrayList<Line>();
	}

	@Override
	public void generate(LevelChunk chunk) {

		lines.addAll(ChunkGraphGenerator.generateHorizontals(chunk));
		lines.addAll(ChunkGraphGenerator.generateVerticals(chunk));
		// lines.add(new Line(new Vec2f(0,8),new Vec2f(16,8),false));
	}

	@Override
	public ComplexCollision checkCollision(float xStart, float yStart, float xEnd, float yEnd, boolean ignoreThin,
			BoundBox box) {

		Vec2f p0 = null;
		Line intersect = null;
		float distance = 64;
		for (int i = 0; i < lines.size(); i++) {
			if (!ignoreThin || (!lines.get(i).isThinLine() && ignoreThin)) {
				Vec2f p1 = lines.get(i).check(xStart, yStart, xEnd, yEnd, box);
				if (p1 != null) {
					float d = p1.getDistance(xStart, yStart);
					if (d < distance) {
						p0 = p1;
						distance = d;
						intersect = lines.get(i);
					}
				}
			}
		}
		if (p0 != null) {
			return new ComplexCollision(p0, intersect, distance);
		}
		return null;
	}

	@Override
	public List<Line> getLines() {
		return lines;
	}

}
