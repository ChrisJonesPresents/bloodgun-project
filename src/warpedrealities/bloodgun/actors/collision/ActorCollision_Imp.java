package warpedrealities.bloodgun.actors.collision;

import warpedrealities.bloodgun.collisionHandling.ComplexCollision;
import warpedrealities.bloodgun.collisionHandling.graph.BoundBox;
import warpedrealities.bloodgun.collisionHandling.graph.Line;
import warpedrealities.bloodgun.collisionHandling.graph.ParentedLine;
import warpedrealities.core.shared.Vec2f;

public class ActorCollision_Imp implements ActorCollision {

	private Vec2f position;
	private ParentedLine[] lines;

	public ActorCollision_Imp(Vec2f position) {
		this.position = position;
		lines = new ParentedLine[4];
		lines[0] = new ParentedLine(new Vec2f(-0.5F, -0.5F), new Vec2f(-0.5F, 0.5F), false, position);
		lines[1] = new ParentedLine(new Vec2f(-0.5F, 0.5F), new Vec2f(0.5F, 0.5F), false, position);
		lines[2] = new ParentedLine(new Vec2f(0.5F, -0.5F), new Vec2f(0.5F, 0.5F), false, position);
		lines[3] = new ParentedLine(new Vec2f(-0.5F, -0.5F), new Vec2f(0.5F, -0.5F), false, position);
	}

	private ComplexCollision compileCollision(Vec2f p, Line l, float xStart, float yStart) {
		ComplexCollision c = new ComplexCollision(p, l, p.getDistance(xStart, yStart));

		return c;
	}

	@Override
	public ComplexCollision check(float xStart, float yStart, float xEnd, float yEnd, BoundBox box1) {
		ComplexCollision c = null;
		if (xStart > position.x) {
			// 2
			Vec2f p = lines[2].check(xStart, yStart, xEnd, yEnd, box1);
			if (p != null) {
				c = compileCollision(p, lines[2], xStart, yStart);
			}

		}
		if (xStart < position.x) {
			// 0
			Vec2f p = lines[0].check(xStart, yStart, xEnd, yEnd, box1);
			if (p != null) {
				c = compileCollision(p, lines[0], xStart, yStart);
			}
		}
		if (yStart > position.y) {
			// 1
			Vec2f p = lines[1].check(xStart, yStart, xEnd, yEnd, box1);
			if (p != null) {
				c = compileCollision(p, lines[1], xStart, yStart);
			}
		}
		if (yStart < position.y) {
			// 3
			Vec2f p = lines[3].check(xStart, yStart, xEnd, yEnd, box1);
			if (p != null) {
				c = compileCollision(p, lines[3], xStart, yStart);
			}
		}
		return c;
	}

}
