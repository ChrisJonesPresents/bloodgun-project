package warpedrealities.bloodgun.level;

import java.util.ArrayList;
import java.util.List;

import warpedrealities.bloodgun.level.Tile.Collision;

public class Level {

	private List<LevelChunk> chunks;

	public Level() {
		chunks = new ArrayList<LevelChunk>();
		generateChunk(false);
		generateChunk(false);
	}

	public void generateChunk(boolean left) {
		LevelChunk edgeChunk = getEdgeChunk(left);
		int initialPosition = getInitialPosition(left, edgeChunk);
		Tile[] boundary = null;

		if (left) {
			if (edgeChunk != null) {
				boundary = edgeChunk.getTiles()[0];
			}
			LevelChunk chunk = new LevelChunk(initialPosition);
			chunk.generate(null, boundary);
			chunks.add(0, chunk);
		} else {
			if (edgeChunk != null) {
				boundary = edgeChunk.getTiles()[15];
			}
			LevelChunk chunk = new LevelChunk(initialPosition);
			chunk.generate(boundary, null);
			chunks.add(chunk);
		}
	}

	private LevelChunk getEdgeChunk(boolean left) {
		if (chunks.size() > 0) {
			if (left) {
				return chunks.get(0);
			} else {
				return chunks.get(chunks.size() - 1);
			}
		}
		return null;
	}

	private int getInitialPosition(boolean left, LevelChunk chunk) {
		if (chunk != null) {
			if (left) {
				return chunk.getPosition() - 16;
			} else {
				return chunk.getPosition() + 16;
			}
		}
		return 0;
	}

	public List<LevelChunk> getChunks() {
		return chunks;
	}

	public Tile.Collision getCollision(float x, float y) {
		if (x < 1) {
			return Collision.SOLID;
		}
		int chunkNum = (int) x / 16;
		if (chunkNum >= chunks.size()) {
			generateChunk(false);
		}
		LevelChunk chunk = chunks.get(chunkNum);

		x = x - (chunkNum * 16);

		return chunk.getCollision(x, y);

	}

}
