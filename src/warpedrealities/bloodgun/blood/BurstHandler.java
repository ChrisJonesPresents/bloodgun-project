package warpedrealities.bloodgun.blood;

import java.util.ArrayList;
import java.util.List;

import warpedrealities.bloodgun.blood.burstFactory.BurstFactory;
import warpedrealities.bloodgun.blood.bursts.DirectionalBurst;
import warpedrealities.bloodgun.blood.bursts.ParticleBurst;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.core.shared.Vec2f;

public class BurstHandler {

	private List<ParticleBurst> bursts;
	private int freeBursts;
	private BurstFactory factory;
	
	public BurstHandler(BurstFactory factory)
	{
		this.factory=factory;
		freeBursts=0;
		bursts=new ArrayList<ParticleBurst>();
	}
	
	public void update(float dt)
	{
		for (int i=0;i<bursts.size();i++)
		{
			if (bursts.get(i).isAlive())
			{
				bursts.get(i).update(dt);
				if (!bursts.get(i).isAlive())
				{
					freeBursts++;
				}
			}
		}
	}
	
	public void addBurst(Vec2f position, Vec2f vector,CollisionHandler collisionHandler)
	{
		if (freeBursts>0)
		{
			for (int i=0;i<bursts.size();i++)
			{
				if (!bursts.get(i).isAlive())
				{
					bursts.get(i).initialize(position, vector, collisionHandler);
					break;
				}
			}
			freeBursts--;
		}
		else
		{
			ParticleBurst burst=factory.buildBurst();
			burst.initialize(position, vector, collisionHandler);
			bursts.add(burst);
		}
		
		
	}
	
	
}
