package warpedrealities.bloodgun.collisionHandling.graph;

public class BoundBox {
	protected float cornerX, cornerY;
	protected float sizeX, sizeY;

	public BoundBox(float x0, float y0, float x1, float y1) {
		cornerX = Math.min(x0, x1);
		cornerY = Math.min(y0, y1);
		sizeX = Math.max(x0, x1) - cornerX;
		sizeY = Math.max(y0, y1) - cornerY;
	}

	public boolean contains(float x, float y) {
		if (x >= cornerX && y >= cornerY && x <= sizeX + cornerX && y <= sizeY + cornerY) {
			return true;
		}
		return false;
	}

	public boolean contains(double x, double y) {
		if (x >= cornerX && y >= cornerY && x <= sizeX + cornerX && y <= sizeY + cornerY) {
			return true;
		}
		return false;
	}

}
