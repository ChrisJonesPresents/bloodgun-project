package warpedrealities.bloodgun.level;

import warpedrealities.bloodgun.collisionHandling.graph.chunkGraph.ChunkGraph;
import warpedrealities.bloodgun.collisionHandling.graph.chunkGraph.ChunkGraph_Imp;
import warpedrealities.bloodgun.level.Tile.Collision;

public class LevelChunk {

	private Tile[][] tiles;
	private int position;
	private ChunkGraph graph;

	public LevelChunk(int position) {
		this.position = position;
		tiles = new Tile[16][];
		for (int i = 0; i < 16; i++) {
			tiles[i] = new Tile[16];
		}

	}

	public void generate(Tile[] left, Tile[] right) {
		tiles = new LevelGenerator().generateChunkGeometry(tiles, left, right);

		new LevelImprover(tiles).improveLevel();

		graph = new ChunkGraph_Imp();
		graph.generate(this);
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public int getPosition() {
		return position;
	}

	public Collision getCollision(int x, int y) {
		int xi = (int) x;
		int yi = (int) y;
		if ((xi >= 0 && xi < 16) && (yi >= 0 && yi < 16)) {
			return tiles[xi][yi].getCollision();
		}
		return Collision.SOLID;
	}

	public Collision getCollision(float x, float y) {
		int xi = (int) x;
		int yi = (int) y;
		float xr = x % 1;
		float yr = y % 1;
		if ((xi >= 0 && xi < 16) && (yi >= 0 && yi < 16)) {
			return tiles[xi][yi].getCollision(xr, yr);
		}
		return Collision.SOLID;
	}

	public ChunkGraph getGraph() {
		return graph;
	}

}
