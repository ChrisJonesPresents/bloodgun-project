package warpedrealities.bloodgun.scenes.gibs.impl;

import warpedrealities.bloodgun.blood.ParticleManager;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.rendering.Platformer_Renderer;
import warpedrealities.bloodgun.scenes.gibs.Gib;
import warpedrealities.bloodgun.scenes.gibs.GibHandler;
import warpedrealities.core.shared.Vec2f;

public class GibHandler_Impl implements GibHandler {

	private Gib []gibs;
	private int index;
	private GibCollisionHandler gibCollision;
	private CollisionHandler collisionHandler;
	private ParticleManager particleManager;
	
	public GibHandler_Impl(Platformer_Renderer renderer,CollisionHandler collisionHandler,ParticleManager particleManager)
	{
		this.particleManager=particleManager;
		this.gibCollision=new GibCollisionHandler(collisionHandler);
		gibs=new Gib[128];
		generateGibs(renderer);
	}
	
	private void generateGibs(Platformer_Renderer renderer)
	{
		for (int i=0;i<128;i++)
		{
			gibs[i]=new Gib(particleManager);
			renderer.addSprite(gibs[i].getSprite(), "gibs.png");
		}
	}
	
	
	@Override
	public void addGib(Vec2f position, Vec2f velocity, int limb) {
		int limit=0;
		while (limit<128)
		{
			if (!gibs[index].isActive())
			{
				gibs[index].setPosition(position);
				gibs[index].setVelocity(velocity);
				gibs[index].setLimb(limb);
				gibs[index].activate();
				break;
			}
			limit++;
			index++;
			if (index>=128)
			{
				index=0;
			}
		}
	}
	
	public void update(float dt)
	{
		for (int i=0;i<gibs.length;i++)
		{
			if (gibs[i].isActive())
			{
				gibs[i].update(dt);
				if (gibCollision.checkGib(gibs[i]))
				{
					particleManager.addBurst("omni",gibs[i].getPosition(),new Vec2f(0,2));
				}
			}
		}
	}
}
