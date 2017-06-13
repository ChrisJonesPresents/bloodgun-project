package warpedrealities.bloodgun.collisionHandling;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.collisionHandling.graph.Line;
import warpedrealities.core.shared.Vec2f;

public class ComplexCollision {

	private Vec2f position;
	private boolean isVertical;
	private Actor actor;
	private float distance;

	public ComplexCollision(Vec2f p0, Line intersect, float distance) {
		position = p0;
		isVertical = intersect.isVertical();
		this.distance = distance;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Vec2f getPosition() {
		return position;
	}

	public boolean isVertical() {
		return isVertical;
	}

	public float getDistance() {
		return distance;
	}

}
