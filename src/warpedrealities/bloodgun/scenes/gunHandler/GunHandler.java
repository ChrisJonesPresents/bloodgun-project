package warpedrealities.bloodgun.scenes.gunHandler;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.NPC;
import warpedrealities.bloodgun.actors.impl.Corpse;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.collisionHandling.ComplexCollision;
import warpedrealities.bloodgun.scenes.World;
import warpedrealities.core.core.GameManager;
import warpedrealities.core.input.MouseHook;
import warpedrealities.core.rendering.Sprite;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.shared.Vec2f;

public class GunHandler {

	private SpriteManager spriteManager;
	private CollisionHandler collisionHandler;
	private BulletHandler bulletHandler;
	private World world;
	private List<Actor> actors;
	private float clock;

	public GunHandler(CollisionHandler collision, BulletHandler bulletHandler,SpriteManager spriteManager, World world) {
		this.collisionHandler = collision;
		this.world=world;
		this.actors=world.getActors();
		this.bulletHandler = bulletHandler;
		this.spriteManager=spriteManager;
	}

	private boolean handleGun(Vec2f position, Double angle) {
		if (MouseHook.getInstance().click()) {
			angle += -0.1D + (GameManager.getRandom().nextDouble() / 5);
			Vec2f p = getVector(position, angle, 12);
			ComplexCollision collision = collisionHandler.getLineIntersect(position.x, position.y + 0.1F, p.x, p.y,
					true);
			float d = 12;
			if (collision != null) {
				d = collision.getPosition().getDistance(position);
				if (NPC.class.isInstance(collision.getActor()))
				{
					NPC npc=(NPC)collision.getActor();
					npc.getShotHandler().handleShot(position, collision.getPosition(),spriteManager,world);
				}
			}
			bulletHandler.addBullet(position, angle, d);
			return true;
		}
		return false;
	}

	public void update(float dt, Vec2f position, Double angle) {
		if (clock > 0) {
			clock -= dt;
		}
		if (clock <= 0) {
			if (handleGun(position, angle)) {
				clock = 0.1F;
			}
		}

		bulletHandler.update(dt);

	}

	private Vec2f getVector(Vec2f position, Double angle, float d) {
		double x = d * Math.cos(angle);
		double y = -d * Math.sin(angle);

		return new Vec2f(position.x + x, position.y + y);
	}

	public void removeBullets() {

		bulletHandler.removeBullets();
	}

}
