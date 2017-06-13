package warpedrealities.bloodgun.collisionHandling.graph.chunkGraph;

import java.util.ArrayList;
import java.util.List;

import warpedrealities.bloodgun.collisionHandling.graph.Line;
import warpedrealities.bloodgun.level.LevelChunk;
import warpedrealities.bloodgun.level.Tile;
import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.core.shared.Vec2f;

public class ChunkGraphGenerator {

	public static List<Line> generateVerticals(LevelChunk chunk) {
		List<Line> list = new ArrayList<Line>();

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 16; j++) {
				j += checkVertical(chunk, i, j, list);
			}
		}

		return list;
	}

	private static int checkVertical(LevelChunk chunk, int x, int y, List<Line> list) {
		// check for solid adjacent to air
		int length = 0;
		while (true) {
			if (length + y >= 16) {
				break;
			}
			Collision collisionLeft = chunk.getCollision(x, y + length);
			Collision collisionRight = chunk.getCollision(x + 1, y + length);
			if (collisionLeft == Collision.SOLID) {
				if (collisionRight != Collision.SOLID) {
					length++;
				} else {
					break;
				}
			}
			if (collisionRight == Collision.SOLID) {
				if (collisionLeft != Collision.SOLID) {
					length++;
				} else {
					break;
				}
			}
			if (collisionRight != Collision.SOLID && collisionLeft != Collision.SOLID) {
				break;
			}

		}
		if (length > 0) {
			list.add(new Line(new Vec2f(chunk.getPosition() + x + 1 - 0.0F, y - 0.0F),
					new Vec2f(chunk.getPosition() + x + 1 - 0.0F, y + length - 0.0F), false));
		}
		return length;
	}

	public static List<Line> generateHorizontals(LevelChunk chunk) {
		List<Line> list = new ArrayList<Line>();

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				j += checkHorizontal(chunk, j, i, list);
			}
		}

		return list;

	}

	private static int checkHorizontal(LevelChunk chunk, int x, int y, List<Line> list) {
		int length = 0;
		int thin = 0;
		boolean exception = false;
		boolean breakBoolean = false;
		while (!breakBoolean) {
			if (length + x >= 16) {
				break;
			}
			Collision collision = chunk.getCollision(x + length, y);
			if (collision == Collision.PLATFORM) {
				int v = 0;
			}
			if (collision == Collision.SOLID) {
				if (chunk.getCollision(x + length, y + 1) == Collision.SOLID) {
					collision = Collision.EMPTY;
					breakBoolean = true;
				} else {
					switch (thin) {
					case 0:
						thin = 1;
						length++;
						break;
					case 1:
						length++;
						break;
					case 2:
						exception = true;
						breakBoolean = true;
						break;
					}
				}
			}

			if (collision == Collision.PLATFORM) {
				switch (thin) {
				case 0:
					thin = 2;
					length++;
					break;
				case 1:
					breakBoolean = true;
					exception = true;
					break;
				case 2:
					length++;
					break;
				}
			}

			if (collision == Collision.EMPTY) {
				breakBoolean = true;
			}

		}
		if (length > 0) {
			if (thin == 2) {
				list.add(new Line(new Vec2f(chunk.getPosition() + x - 0.0F, y + 1 - 0.0F),
						new Vec2f(chunk.getPosition() + x + length - 0.0F, y + 1 - 0.0F), true));
			} else {
				list.add(new Line(new Vec2f(chunk.getPosition() + x - 0.0F, y + 1 - 0.0F),
						new Vec2f(chunk.getPosition() + x + length - 0.0F, y + 1 - 0.0F), false));
			}

		}
		if (exception) {
			return length - 1;
		}
		return length;
	}
}
