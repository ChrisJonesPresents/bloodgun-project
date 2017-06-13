package warpedrealities.bloodgun.level;

import warpedrealities.bloodgun.level.Tile.Collision;

/**
 * tool to make the level tiles look slightly nicer by making the tile images
 * contextual
 * 
 *
 */
public class LevelImprover {

	private Tile[][] grid;

	public LevelImprover(Tile[][] grid) {
		this.grid = grid;
	}

	private boolean isSolid(int x, int y) {
		if (y > 15 || x < 0 || x > 15) {
			return true;
		}

		if (grid[x][y].getCollision() == Collision.SOLID) {
			return true;
		}
		return false;
	}

	private boolean isPlatform(int x, int y) {
		if (y > 15 || x < 0 || x > 15) {
			return true;
		}
		if (grid[x][y].getCollision() == Collision.SOLID || grid[x][y].getCollision() == Collision.PLATFORM) {
			return true;
		}
		return false;
	}

	private int getSolid(int x, int y) {
		boolean top = isSolid(x, y + 1);
		boolean left = isSolid(x - 1, y);
		boolean right = isSolid(x + 1, y);

		int tileImage = 5;
		if (top) {
			tileImage += 4;
		}
		if (left && !right) {
			tileImage += 2;
		}
		if (!left && right) {
			tileImage += 1;
		}
		if (!left && !right) {
			tileImage += 3;
		}
		return tileImage;
	}

	private int getPlatform(int x, int y) {
		boolean left = isPlatform(x - 1, y);
		boolean right = isPlatform(x + 1, y);
		int tileImage = 1;
		if (left && !right) {
			tileImage = 3;
		}
		if (!left && right) {
			tileImage = 2;
		}
		if (!left && !right) {
			tileImage = 4;
		}

		return tileImage;
	}

	public void improveLevel() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				switch (grid[i][j].getCollision()) {
				case SOLID:
					grid[i][j].setForeground(getSolid(i, j));
					break;
				case PLATFORM:
					grid[i][j].setForeground(getPlatform(i, j));
					break;
				case EMPTY:
					grid[i][j].setBackground(1);
				}
			}
		}
	}
}
