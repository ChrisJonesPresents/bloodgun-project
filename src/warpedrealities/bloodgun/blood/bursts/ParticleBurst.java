package warpedrealities.bloodgun.blood.bursts;

import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.core.shared.Vec2f;

public interface ParticleBurst {

	boolean isAlive();

	void update(float dt);

	void initialize(Vec2f origin, Vec2f vector, CollisionHandler collisionHandler);

}
