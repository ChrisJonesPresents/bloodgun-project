package warpedrealities.bloodgun.collisionHandling.graph;

import warpedrealities.core.shared.Vec2f;

public class ParentedLine extends Line {

	private Vec2f parent;
	private Vec2f offsetStart;
	private Vec2f offsetEnd;

	public ParentedLine(Vec2f start, Vec2f end, boolean thin, Vec2f parent) {
		super(start, end);
		this.parent = parent;
		this.offsetEnd = new Vec2f(parent.x + end.x, parent.y + end.y);
		this.offsetStart = new Vec2f(parent.x + start.x, parent.y + start.y);
		this.box = new ParentedBoundBox(start.x, start.y, end.x, end.y, parent);
	}

	public void setParent(Vec2f parent) {
		this.parent = parent;
	}

	@Override
	public BoundBox getBox() {
		return box;
	}

	@Override
	public Vec2f getStart() {
		offsetStart.x = start.x + parent.x;
		offsetStart.y = start.y + parent.y;
		return offsetStart;
	}

	@Override
	public Vec2f getEnd() {
		offsetEnd.x = end.x + parent.x;
		offsetEnd.y = end.y + parent.y;
		return offsetEnd;
	}

}
