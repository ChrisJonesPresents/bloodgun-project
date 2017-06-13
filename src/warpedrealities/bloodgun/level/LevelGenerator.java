package warpedrealities.bloodgun.level;

import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.core.core.GameManager;
import warpedrealities.core.shared.Vec2i;

/**
 * 
 * procedural generator to generate the tiles for a chunk of the level
 *
 */

public class LevelGenerator {

	public LevelGenerator() {

	}

	public Tile[][] generateChunkGeometry(Tile[][] grid, Tile[] left, Tile[] right) {
		int leftHeight = 1;
		if (left != null) {
			for (int i = 0; i < 16; i++) {
				grid[0][i] = new Tile(left[i].getCollision());
				if (left[i].getCollision() == Collision.SOLID) {
					leftHeight = i;
				}
			}
		}
		int rightHeight = 1;
		if (right != null) {
			for (int i = 0; i < 16; i++) {
				grid[15][i] = new Tile(right[i].getCollision());
				if (right[i].getCollision() == Collision.SOLID) {
					rightHeight = i;
				}
			}
		}

		generateFloor(grid, leftHeight, rightHeight);
		generatePlatforms(grid);
		fillEmpty(grid);
		placeBackground(grid);
		return grid;
	}

	private Tile[][] generateFloor(Tile[][] grid, int left, int right) {
		int height = left;
		boolean tooTall = false;
		if (height > 4 && GameManager.getRandom().nextBoolean()) {
			tooTall = true;
		}
		for (int i = 1; i < 16; i++) {
			height = getModifier(height, tooTall);
			for (int j = 0; j <= height; j++) {
				grid[i][j] = new Tile(Collision.SOLID);
			}
		}
		return grid;
	}

	private int getModifier(int original, boolean tooTall) {
		int r = GameManager.getRandom().nextInt(6);

		int mod = 0;
		switch (r) {
		case 0:
			mod = 1;
			break;
		case 1:
			if (original < 8) {
				mod = 1;
			} else {
				mod = -1;
			}
			break;

		case 2:
			if (original > 8) {
				mod = -1;
			}
			break;
		case 3:
			if (tooTall) {
				mod = -1;
			}
			break;
		case 4:
			if (tooTall) {
				mod = -2;
			}
			break;
		case 5:
			mod = -1;
			break;
		case 6:
			mod = -2;
			break;
		}
		if (original + mod < 1 || original + mod > 14) {
			mod = 0;
		}
		return original + mod;
	}

	private Tile[][] generatePlatforms(Tile[][] grid) {

		Vec2i[] platforms = new Vec2i[3 + GameManager.getRandom().nextInt(6)];
		int platformIndex = 0;
		// check for existing platforms
		for (int i = 0; i < 16; i++) {
			if (grid[0][i] != null && grid[0][i].getCollision() == Collision.PLATFORM) {
				platforms[0] = new Vec2i(0, i);
				platformIndex++;
				break;
			}
		}

		int escape = 0;
		while (platformIndex < platforms.length && escape < 255) {
			int rx = GameManager.getRandom().nextInt(15) + 1;
			int ry = GameManager.getRandom().nextInt(14) + 1;
			if (grid[rx][ry] == null) {
				platforms[platformIndex] = new Vec2i(rx, ry);
				platformIndex++;
			} else {
				escape++;
			}
		}

		for (int i = 0; i < platforms.length; i++) {
			if (platforms[i] != null) {
				int platformLength = 2 + GameManager.getRandom().nextInt(4);
				for (int j = 0; j < platformLength; j++) {
					if (j + platforms[i].x > 15) {
						break;
					}
					if (grid[j + platforms[i].x][platforms[i].y] == null) {
						grid[j + platforms[i].x][platforms[i].y] = new Tile(Collision.PLATFORM);
					}

				}
			}
		}
		return grid;
	}

	private Tile[][] fillEmpty(Tile[][] grid) {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (grid[i][j] == null) {
					grid[i][j] = new Tile(Collision.EMPTY);
				}
			}
		}
		return grid;
	}

	private Tile[][] placeBackground(Tile[][] grid) {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (grid[i][j] != null) {
					grid[i][j].setBackground(1);
				}
			}
		}

		return grid;
	}
}
