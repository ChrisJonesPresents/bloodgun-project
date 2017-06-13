package warpedrealities.bloodgun.level;

import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.core.core.Tile_Int;

public class Tile implements Tile_Int {

	public enum Collision {
		EMPTY, PLATFORM, SOLID
	};

	private Collision collision;
	private int foreground;
	private int background;

	public Tile(Collision collision) {
		this.collision = collision;

	}

	public void setBackground(int background) {
		this.background = background;
	}

	@Override
	public int getBackground() {

		return background;
	}

	@Override
	public int getForeground() {
		return foreground;
	}

	public void setForeground(int foreground) {
		this.foreground = foreground;
	}

	public Collision getCollision() {
		return collision;
	}

	public Collision getCollision(float x, float y) {
		if (collision == Collision.PLATFORM) {
			if (y > 0.8F && y < 1.0F) {
				return collision;
			} else {
				return Collision.EMPTY;
			}
		}
		return collision;
	}

}
