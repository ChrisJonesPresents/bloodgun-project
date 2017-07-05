package warpedrealities.bloodgun.blood;

import warpedrealities.bloodgun.blood.bursts.DirectionalBurst;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.core.core.GameManager;
import warpedrealities.core.shared.Vec2f;

public class ParticleEmitter {

	private Vec2f position;
	private Particle []particles;
	private float clock;
	
	public ParticleEmitter(Vec2f p) {
		position=p;
	}
	
	public void setParticles(Particle [] particles)
	{
		this.particles=particles;
	}

	
	public Particle[] getParticles() {
		return particles;
	}

	private Vec2f getVector()
	{
		float r=GameManager.getRandom().nextFloat()*8;
		Vec2f v=new Vec2f(0,2);
		v.rotate(r);
		
		return v;
	}
	
	public void update(float dt) {

		for (int i=0;i<particles.length;i++)
		{
			particles[i].update(dt);
		}
	}

	public void generate(float dt) {
		clock+=dt;
		if (clock>0.125F)
		{
			for (int i=0;i<particles.length;i++)
			{
				if (!particles[i].isAlive())
				{
					Vec2f v=getVector();
					particles[i].spawn(new Vec2f(position.x,position.y), v, 0.25F);
					break;
				}
			}
			clock=0;
		}	
	}

}
