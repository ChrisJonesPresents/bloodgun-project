package warpedrealities.bloodgun.actors.impl;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.NPC;
import warpedrealities.bloodgun.actors.animators.Animator;
import warpedrealities.bloodgun.actors.animators.Corpse_Animator;
import warpedrealities.bloodgun.actors.collision.ActorCollision;
import warpedrealities.bloodgun.actors.collision.ActorCollision_Imp;
import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.actors.movement.CorpseMovement;
import warpedrealities.bloodgun.actors.shotHandler.ShotHandler;
import warpedrealities.bloodgun.actors.shotHandler.impl.CorpseShotHandler;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.core.rendering.Sprite;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.rendering.Square_Rotatable_Int;
import warpedrealities.core.shared.Vec2f;

public class Corpse implements Actor, NPC {

	private Vec2f position;
	private CollisionHandler collisionHandler;
	private boolean active;
	private ShotHandler shotHandler;
	private Square_Rotatable_Int sprites[];
	private Corpse_Animator animator;
	private CorpseMovement movement;
	protected AnimationState animationState;
	private ActorCollision actorCollision;
	
	public Corpse(CollisionHandler collisionHandler, Vec2f position, Vec2f impact, Square_Rotatable_Int[] sprites) {
		this.collisionHandler=collisionHandler;
		this.shotHandler=new CorpseShotHandler(this);
		this.position=position;
		this.sprites=sprites;
		this.movement=new CorpseMovement(position.x-impact.x,position.y-impact.y);
		this.animator=new Corpse_Animator(sprites);
		this.actorCollision = new ActorCollision_Imp(this.position);
		if (this.movement.getVelocity().x<0)
		{
			for (int i=0;i<6;i++)
			{
				sprites[i].setFlipped(true);

			}		
		}
	}

	@Override
	public Vec2f getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public void update(float DT) {
		
		movement.handleCollision(position, collisionHandler);
		movement.update(position, DT);

		this.animator.animate(DT, movement.getVelocity(), !movement.isFalling(),
				movement.getState(), getAnimationState());
		for (int i=0;i<sprites.length;i++)
		{
			sprites[i].reposition(position.x, position.y);
		}
	}

	@Override
	public boolean isActive() {
		return movement.isFalling();
	}


	@Override
	public void setAnimationState(AnimationState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public AnimationState getAnimationState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActorCollision getCollision() {
		return actorCollision;
	}

	@Override
	public void removeSprites() {
		for (int i=0;i<sprites.length;i++)
		{
			sprites[i].remove();
		}

	}


	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(float x, float f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ShotHandler getShotHandler() {
		return shotHandler;
	}

	@Override
	public boolean removalNeeded() {
		return true;
	}

	public int applyShot(float x, float y) {
		movement.applyForce(x,y);
		if (y>=0)
		{
			return animator.applyWound(true);
		}
		else
		{
			return animator.applyWound(false);
		}
	}


}
