package warpedrealities.bloodgun.collisionHandling.graph;

import warpedrealities.core.shared.Vec2f;

public class ParentedBoundBox extends BoundBox {

	protected Vec2f parent;

	public ParentedBoundBox(float x0, float y0, float x1, float y1, Vec2f parent) {
		super(x0, y0, x1, y1);
		this.parent = parent;
	}

	@Override
	public boolean contains(float x, float y) {
		if (x >= parent.x + cornerX && y >= cornerY + parent.y && x <= sizeX + parent.x + cornerX
				&& y <= sizeY + cornerY + parent.y) {
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(double x, double y) {
		if (x >= parent.x + cornerX && y >= cornerY + parent.y && x <= sizeX + parent.x + cornerX
				&& y <= sizeY + cornerY + parent.y) {
			return true;
		}
		return false;
	}

}
