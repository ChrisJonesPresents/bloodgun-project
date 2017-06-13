package warpedrealities.bloodgun.actors.AI;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.NPC;
import warpedrealities.bloodgun.actors.impl.Player;
import warpedrealities.bloodgun.actors.movement.HumanoidMovement;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.core.shared.Vec2f;

public interface Controller {

	public void setTarget(Player player);

	public void setControlledNPC(NPC npc);

	void update(float dt, CollisionHandler collisionHandler, HumanoidMovement handler, Vec2f position);
}
