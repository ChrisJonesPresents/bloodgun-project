package warpedrealities.bloodgun.scenes.gibs;

import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.core.core.GameManager;
import warpedrealities.core.rendering.Sprite;
import warpedrealities.core.rendering.Sprite_Rotatable;
import warpedrealities.core.shared.Vec2f;

public class Gib {
	
	private static final float GRAVITY=4.0F;
	private static final float TERMINALVELOCITY=8.0F;
	private Sprite_Rotatable sprite;
	private Vec2f position;
	private Vec2f velocity;
	private boolean active;
	private float clock;

	
	public Gib()
	{
		active=false;
		clock=0;
		sprite=new Sprite_Rotatable(new Vec2f(0, 0), 0.5F, 16);

	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public Vec2f getPosition() {
		return position;
	}

	public Vec2f getVelocity() {
		return velocity;
	}

	public boolean isActive() {
		return active;
	}

	public float getClock() {
		return clock;
	}

	public void setPosition(Vec2f position) {

		this.position=new Vec2f(position.x,position.y);
		sprite.reposition(this.position.x,this.position.y);
	}

	public void setVelocity(Vec2f velocity) {
	
		this.velocity=new Vec2f(velocity.x,velocity.x);
		this.velocity.normalize();
		this.velocity.x*=4;
		this.velocity.y*=4;
	}

	private void makeFrame(int type)
	{
		int r=GameManager.getRandom().nextInt(3);
		sprite.setImage((r*4)+type);
	}

	public void setLimb(int limb) {
		switch (limb)
		{
		
		case 0:
			//torso
			makeFrame(3);
			break;
		case 1:
			//head
			makeFrame(0);
			break;
		case 2:
			//arm
			makeFrame(2);
			break;
		case 3:
			//arm
			makeFrame(2);
			break;
		case 4:
			//leg
			makeFrame(1);
			break;
		case 5:
			//leg
			makeFrame(1);
			break;
		}
	}

	public void activate() {
		sprite.setVisible(true);
		active=true;
		clock=0;	
	}

	public void update(float dt)
	{
		clock+=dt;
		sprite.reposition(position.x,position.y);
		if (velocity.x!=0)
		{
			sprite.setRotation(sprite.getRotation()+(dt*4));
			if (velocity.y>-TERMINALVELOCITY)
			{
				velocity.y-=dt*GRAVITY;
			}
			position.x+=dt*velocity.x;
			position.y+=dt*velocity.y;


		}
		

		if (clock>60)
		{
			active=false;
			sprite.setVisible(false);
		}
	}
}
