package warpedrealities.bloodgun.blood;

import warpedrealities.core.shared.Vec2f;

public class Particle {
	private Vec2f position;
	private Vec2f velocity;
	private float lifespan;
	
	public Particle()
	{
		
	}

	public void spawn(Vec2f p, Vec2f v, float l)
	{
		lifespan=l;
		position=p;
		velocity=v;
	}
	
	public void update(float dt)
	{
		if (isAlive())
		{
			lifespan-=dt;
			position.x+=velocity.x*dt;
			position.y+=velocity.y*dt;
		}
	}

	public Vec2f getPosition() {
		return position;
	}

	public Vec2f getVelocity() {
		return velocity;
	}

	public float getLifespan() {
		return lifespan;
	}
	
	public boolean isAlive()
	{
		if (lifespan>0)
		{
			return true;
		}
		return false;
	}
}
