package warpedrealities.bloodgun.scenes;

import warpedrealities.core.shared.Vec2f;

public class Platformer_MouseHandler {

	Vec2f normal;

	public Platformer_MouseHandler() {
		normal = new Vec2f(0, 0);
	}

	public double getMouseAngle(Vec2f position, float x, float y) {
		normal.x = x - position.x;
		normal.y = (y - position.y) * -1;
		normal.normalize();
		double angles = Math.toDegrees(Math.atan2(normal.y, normal.x));
		return angles * 0.0174533F;
	}

}
