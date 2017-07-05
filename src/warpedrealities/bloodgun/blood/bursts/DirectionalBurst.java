package warpedrealities.bloodgun.blood.bursts;

import java.util.ArrayList;
import java.util.Arrays;

import warpedrealities.bloodgun.blood.Particle;
import warpedrealities.bloodgun.blood.ParticleManager;
import warpedrealities.bloodgun.blood.ParticleRenderer;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.collisionHandling.ComplexCollision;
import warpedrealities.core.core.GameManager;
import warpedrealities.core.shared.Vec2f;

public class DirectionalBurst implements ParticleBurst {

	private final static int PARTICLECOUNT=32;
	private Particle []particles;
	private float clock;
	
	public DirectionalBurst(ParticleRenderer particleRenderer)
	{
		particles=new Particle[PARTICLECOUNT];
		for (int i=0;i<PARTICLECOUNT;i++)
		{
			particles[i]=new Particle();
		}
		
		particleRenderer.addParticles(Arrays.asList(particles));
	}
	
	public boolean isAlive()
	{
		if (clock>0)
		{
			return true;
		}
		return false;
	}
	
	public void update(float dt) {
		if (clock>0)
		{
			for (int i=0;i<PARTICLECOUNT;i++)
			{
				particles[i].update(dt);
			}
			clock-=dt;
		}
	}
	
	public void initialize(Vec2f p, Vec2f v,CollisionHandler collisionHandler) {
		
		Vec2f furthest=v.replicate();
		furthest.setLength(16);
		if (furthest.x<0)
		{
			furthest.x=0;
		}
		float l=8;
		//calculate how far to the nearest surface
		ComplexCollision collision=collisionHandler.getWorldLineIntersect(p.x, p.y,furthest.x , furthest.y, true);
		if (collision!=null)
		{
			l=collision.getDistance();
		}
		else
		{
			l=p.getDistance(furthest);
		}
		
		clock=l/8;
		for (int i=0;i<PARTICLECOUNT;i++)
		{
				//vary up angles
				float r=GameManager.getRandom().nextFloat()*0.1F;
				Vec2f variant=v.replicate();
				variant.rotate(-0.05F+r);
				r=GameManager.getRandom().nextFloat()*3.2F;
				variant.modLength(-1.6F+r);
				particles[i].spawn(p.replicate(), variant, clock);			
		}
	}
	
}
