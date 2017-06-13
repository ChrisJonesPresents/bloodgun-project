package warpedrealities.bloodgun.actors.movement;

import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.movementqualities.NinjaQualities;
import warpedrealities.core.shared.Vec2f;

public class CorpseMovement extends CommonMovement{


	
	public CorpseMovement(float x, float y) {
		qualities=new NinjaQualities();
		state=State.FALLING;
		if (y<0.1F)
		{
			y=0.1F;
		}
		velocity=new Vec2f(x,y);
		velocity.normalize();
		velocity.x*=4;
		velocity.y*=4;
	}

	public void applyForce(float x, float y) {

		Vec2f p=new Vec2f(x,y);
		p.normalize();
		velocity.x+=p.x;
		velocity.y+=p.y;
		
	}


}
