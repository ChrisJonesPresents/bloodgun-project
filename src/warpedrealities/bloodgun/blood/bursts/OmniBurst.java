package warpedrealities.bloodgun.blood.bursts;

import java.util.Arrays;

import warpedrealities.bloodgun.blood.Particle;
import warpedrealities.bloodgun.blood.ParticleManager;
import warpedrealities.bloodgun.blood.ParticleRenderer;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.collisionHandling.ComplexCollision;
import warpedrealities.core.core.GameManager;
import warpedrealities.core.shared.Vec2f;

public class OmniBurst implements ParticleBurst {

	private final static int PARTICLECOUNT=32;
	private Particle []particles;
	private float clock;
	
	public OmniBurst(ParticleRenderer particleRenderer)
	{
		particles=new Particle[PARTICLECOUNT];
		for (int i=0;i<PARTICLECOUNT;i++)
		{
			particles[i]=new Particle();
		}
		
		particleRenderer.addParticles(Arrays.asList(particles));
	}
	
	@Override
	public boolean isAlive() {
		if (clock>0)
		{
			return true;
		}
		return false;
	}

	@Override
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

	@Override
	public void initialize(Vec2f position, Vec2f vector, CollisionHandler collisionHandler) {
		clock=0.125F;
		for (int i=0;i<PARTICLECOUNT;i++)
		{
				//vary up angles
				float r=GameManager.getRandom().nextFloat()*8;
				Vec2f variant=vector.replicate();
				variant.rotate(-0.05F+r);
				r=GameManager.getRandom().nextFloat()*1.6F;
				variant.modLength(-0.8F+r);
				particles[i].spawn(position.replicate(), variant, clock);			
		}
	}

}
