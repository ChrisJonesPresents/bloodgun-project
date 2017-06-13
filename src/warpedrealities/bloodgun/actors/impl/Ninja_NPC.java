package warpedrealities.bloodgun.actors.impl;

import warpedrealities.bloodgun.actors.NPC;
import warpedrealities.bloodgun.actors.AI.Controller;
import warpedrealities.bloodgun.actors.animators.Ninja_Animator;
import warpedrealities.bloodgun.actors.collision.ActorCollision;
import warpedrealities.bloodgun.actors.collision.ActorCollision_Imp;
import warpedrealities.bloodgun.actors.movement.HumanoidMovement;
import warpedrealities.bloodgun.actors.shotHandler.ShotHandler;
import warpedrealities.bloodgun.actors.shotHandler.impl.NinjaShotHandler;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.director.Director_Int;
import warpedrealities.bloodgun.movementqualities.NinjaQualities;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.rendering.Square_Rotatable_Int;
import warpedrealities.core.shared.Vec2f;

public class Ninja_NPC extends Humanoid implements NPC {

	private boolean active;
	private Director_Int director;
	private Controller controller;
	private ActorCollision actorCollision;
	protected Square_Int spriteSword;
	private ShotHandler shotHandler;
	
	public Ninja_NPC(Vec2f position, Square_Int spriteBody, Square_Int sword, CollisionHandler collisionHandler,
			Director_Int director, Controller controller) {
		this.position = position;
		this.spriteBody = spriteBody;
		this.spriteSword = sword;
		this.spriteBody.setImage(0);
		this.spriteSword.setImage(12);

		this.collisionHandler = collisionHandler;
		this.movementHandler = new HumanoidMovement(new NinjaQualities());
		this.director = director;
		this.bodyAnimator = new Ninja_Animator(this, spriteBody, spriteSword);
		this.active = false;
		this.controller = controller;
		controller.setControlledNPC(this);
		this.actorCollision = new ActorCollision_Imp(this.position);
		this.shotHandler=new NinjaShotHandler(this,director);
	}

	@Override
	public Vec2f getPosition() {
		return position;
	}

	@Override
	public void update(float DT) {
		if (active) {
			controller.update(DT, collisionHandler, movementHandler, position);
			super.update(DT);
			if (bodyAnimator.isReversed()) {
				spriteSword.reposition(position.x + 0.5F, position.y);
			} else {
				spriteSword.reposition(position.x - 0.5F, position.y);
			}

		}
	}

	@Override
	public void activate() {

		this.spriteBody.setVisible(true);
		active = true;
	}

	@Override
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
		this.spriteBody.reposition(position);
	}

	@Override
	public boolean isActive() {

		return true;
	}

	@Override
	public ActorCollision getCollision() {
		return actorCollision;
	}

	@Override
	public void removeSprites() {
		spriteBody.remove();
		spriteSword.remove();
	}

	@Override
	public ShotHandler getShotHandler() {

		return shotHandler;
	}

	@Override
	public boolean removalNeeded() {
		// TODO Auto-generated method stub
		return false;
	}




}
