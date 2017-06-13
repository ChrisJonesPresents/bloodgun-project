package warpedrealities.bloodgun.actors.impl;

import org.lwjgl.glfw.GLFW;

import warpedrealities.bloodgun.actors.animators.Animator;
import warpedrealities.bloodgun.actors.animators.Player_Animator;
import warpedrealities.bloodgun.actors.animators.Player_Arm_Animator;
import warpedrealities.bloodgun.actors.animators.SimpleAnimator;
import warpedrealities.bloodgun.actors.collision.ActorCollision;
import warpedrealities.bloodgun.actors.movement.HumanoidMovement;
import warpedrealities.bloodgun.actors.movement.CommonMovement.State;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.movementqualities.PlayerQualities;
import warpedrealities.core.input.Keyboard;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.rendering.Square_Rotatable_Int;
import warpedrealities.core.shared.Vec2f;

public class Player extends Humanoid {

	private Square_Rotatable_Int spriteArm;
	private SimpleAnimator armAnimator;

	public Player(Vec2f position, Square_Int spriteBody, CollisionHandler collisionHandler, Square_Rotatable_Int arm) {
		this.position = position;
		this.spriteBody = spriteBody;
		this.spriteBody.setVisible(true);
		this.spriteBody.setImage(0);
		this.spriteArm = arm;
		this.spriteArm.setVisible(true);
		this.spriteArm.setImage(0);
		this.spriteArm.setRotation(-1);
		this.spriteArm.reposition(position.x, position.y + 0.1F);
		this.armAnimator = new Player_Arm_Animator(spriteArm);
		this.collisionHandler = collisionHandler;
		this.movementHandler = new HumanoidMovement(new PlayerQualities());
		this.bodyAnimator = new Player_Animator(this.spriteBody);
		alignSprite();
	}

	private void alignSprite() {
		this.spriteBody.reposition(position);
		this.spriteArm.reposition(position);
	}

	@Override
	public Vec2f getPosition() {
		return position;
	}

	@Override
	public void update(float DT) {
		if (animationState == AnimationState.NONE) {
			control(DT);
		}
		armAnimator.animate(DT);

		super.update(DT);

	}

	public void control(float DT) {
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A) || Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
			movementHandler.moveLeft(position, DT);
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D) || Keyboard.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
			movementHandler.moveRight(position, DT);
		}

		if (!movementHandler.isFalling()) {
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S) || Keyboard.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
				movementHandler.drop(position, collisionHandler);
			}
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W) || Keyboard.isKeyDown(GLFW.GLFW_KEY_UP)) {
				movementHandler.jump();
			}
		}

	}

	public void setGunAngle(Double angle) {

		spriteArm.setRotation(angle+1.5708F);
		spriteArm.reposition(position.x, position.y + 0.1F);
		if (angle < -1.57F || angle > 1.57F) {
			bodyAnimator.setReversed(true);
			spriteArm.setFlipped(true);
			spriteBody.setFlipped(true);
		} else {
			bodyAnimator.setReversed(false);
			spriteArm.setFlipped(false);
			spriteBody.setFlipped(false);
		}
	}

	@Override
	public boolean isActive() {
		if (animationState == AnimationState.DYING) {
			return false;
		}
		return true;
	}

	public void kill(Vec2f attackOrigin) {
		spriteArm.setVisible(false);
		animationState = AnimationState.DYING;
	}

	@Override
	public ActorCollision getCollision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSprites() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removalNeeded() {
		// TODO Auto-generated method stub
		return false;
	}

}
