package warpedrealities.bloodgun.scenes.gibs;

import warpedrealities.core.shared.Vec2f;

public interface GibHandler {

	void addGib(Vec2f position, Vec2f velocity, int limb);

	void update(float dt);
}
