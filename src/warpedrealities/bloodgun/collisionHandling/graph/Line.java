package warpedrealities.bloodgun.collisionHandling.graph;

import warpedrealities.core.shared.Vec2f;

public class Line {

	protected Vec2f start;
	protected Vec2f end;
	protected boolean thinLine;
	protected AbLine l0;
	protected AbLine l1;
	protected BoundBox box;
	protected boolean vertical;

	protected Line(Vec2f start, Vec2f end) {
		l0 = new AbLine();
		l1 = new AbLine();
		this.thinLine = false;
		this.end = end;
		this.start = start;

	}

	public Line(Vec2f start, Vec2f end, boolean thin) {
		l0 = new AbLine();
		l1 = new AbLine();
		this.thinLine = thin;
		this.end = end;
		this.start = start;
		box = new BoundBox(start.x, start.y, end.x, end.y);
	}

	public BoundBox getBox() {
		return box;
	}

	public Vec2f getStart() {
		return start;
	}

	public void setStart(Vec2f start) {
		this.start = start;
	}

	public Vec2f getEnd() {
		return end;
	}

	public void setEnd(Vec2f end) {
		this.end = end;
	}

	public boolean isThinLine() {
		return thinLine;
	}

	public boolean isVertical() {
		return vertical;
	}

	class AbLine {
		public double a;
		public double b;
		public double c;

		public void set(float x0, float y0, float x1, float y1) {
			a = y1 - y0;
			b = x0 - x1;
			c = (a * x0) + (b * y0);
		}
	}

	public Vec2f check(float xStart, float yStart, float xEnd, float yEnd, BoundBox box1) {

		l0.set(xStart, yStart, xEnd, yEnd);
		l1.set(getStart().x, getStart().y, getEnd().x, getEnd().y);
		double det = l0.a * l1.b - l1.a * l0.b;
		if (det == 0) {
			return null;
		}
		double x = (l1.b * l0.c - l0.b * l1.c) / det;
		double y = (l0.a * l1.c - l1.a * l0.c) / det;
		if (getBox().contains(x, y) && box1.contains(x, y)) {
			return new Vec2f(x, y);
		}

		return null;
	}

}
