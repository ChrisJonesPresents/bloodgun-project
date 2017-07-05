package warpedrealities.bloodgun.actors.shotHandler.impl;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.impl.Corpse;
import warpedrealities.bloodgun.actors.shotHandler.ShotHandler;
import warpedrealities.bloodgun.scenes.World;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.shared.Vec2f;

public class CorpseShotHandler implements ShotHandler {

	private Corpse actor;
	
	public CorpseShotHandler(Corpse actor) {
		this.actor=actor;
	}

	@Override
	public void handleShot(Vec2f position, Vec2f origin, SpriteManager manager, World world) {
		int limb=actor.applyShot(origin.x-position.x,origin.y-position.y);
		world.getGibHandler().addGib(actor.getPosition(),new Vec2f(origin.x-position.x,origin.y-position.y),limb);
		Vec2f v=new Vec2f(actor.getPosition().x-position.x,actor.getPosition().y-position.y); v.setLength(4);
		world.getParticleManager().addBurst("directional",actor.getPosition(), v);
	}

}
