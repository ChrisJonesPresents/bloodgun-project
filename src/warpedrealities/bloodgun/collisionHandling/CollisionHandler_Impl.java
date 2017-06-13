package warpedrealities.bloodgun.collisionHandling;

import warpedrealities.bloodgun.level.Level;
import warpedrealities.bloodgun.level.LevelChunk;
import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.core.shared.Vec2f;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.collisionHandling.graph.BoundBox;
import warpedrealities.bloodgun.collisionHandling.graph.chunkGraph.ChunkGraph;

public class CollisionHandler_Impl implements CollisionHandler {

	private Level level;
	private List<Actor> actors;

	public CollisionHandler_Impl(Level level, List<Actor> actors) {
		this.level = level;
		this.actors = actors;
	}

	@Override
	public Collision getWorldCollision(float x, float y) {
		return level.getCollision(x, y);
	}

	@Override
	public ComplexCollision getLineIntersect(float xStart, float yStart, float xEnd, float yEnd, boolean ignoreThin) {

		BoundBox box = new BoundBox(xStart, yStart, xEnd, yEnd);
		// process
		// figure out which chunks we need to check using the xstart and xend
		// values
		ChunkGraph graph[] = getGraphs(xStart, xEnd);
		// calculate all possible collisions, then pick the closest to the
		// origin
		ComplexCollision collision = null;
		for (int i = 0; i < 2; i++) {
			if (graph[i] != null && collision == null) {
				collision = graph[i].checkCollision(xStart, yStart, xEnd, yEnd, ignoreThin, box);
			}
		}
		if (ignoreThin) {
			collision = getActorIntersect(xStart, yStart, xEnd, yEnd, collision, box);
		}
		return collision;
	}

	public ComplexCollision getActorIntersect(float xStart, float yStart, float xEnd, float yEnd,
			ComplexCollision collision, BoundBox box) {
		float spanStart = xStart;
		float spanEnd = xEnd;
		if (collision != null) {
			spanEnd = collision.getPosition().x;
		}
		if (spanStart > spanEnd) {
			float xTemp = spanStart;
			spanStart = spanEnd;
			spanEnd = xTemp;
		}
		spanStart -= 1;
		spanEnd += 1;

		for (int i = 0; i < actors.size(); i++) {
			if (actors.get(i).isActive() && actors.get(i).getCollision() != null && actors.get(i).getPosition().x > spanStart
					&& actors.get(i).getPosition().x < spanEnd) {
				ComplexCollision c = actors.get(i).getCollision().check(xStart, yStart, xEnd, yEnd, box);
				if (c != null)
				{
					if (collision!=null && c.getDistance() < collision.getDistance()) 
					{
						collision = c;
						c.setActor(actors.get(i));
					}
					if (collision==null)
					{
						collision = c;
						c.setActor(actors.get(i));
					}
				}
			}
		}
		return collision;
	}

	private ChunkGraph[] getGraphs(float xStart, float xEnd) {
		ChunkGraph[] graphs = new ChunkGraph[2];

		int zero = (int) (xStart / 16);
		int one = (int) (xEnd / 16);
		if (level.getChunks().size() <= one) {
			level.generateChunk(false);
		}

		graphs[0] = level.getChunks().get(zero).getGraph();
		graphs[1] = level.getChunks().get(one).getGraph();
		if (graphs[0] == graphs[1]) {
			graphs[1] = null;
		}
		return graphs;
	}


}
