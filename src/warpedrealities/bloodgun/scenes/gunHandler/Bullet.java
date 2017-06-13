package warpedrealities.bloodgun.scenes.gunHandler;

import warpedrealities.bloodgun.actors.animators.SimpleAnimator;
import warpedrealities.core.rendering.Square_Rotatable_Int;
import warpedrealities.core.shared.Vec2f;

public class Bullet implements SimpleAnimator {

	private static final float BULLETSPEED = 64;
	private Square_Rotatable_Int bulletSprite;
	private Vec2f position;
	private Vec2f velocity;
	private float clock;
	private float duration;

	public Bullet(Square_Rotatable_Int bulletSprite) {
		this.bulletSprite = bulletSprite;
		position = new Vec2f(0, 0);
		velocity = new Vec2f(1, 0);
	}

	public boolean isAlive() {
		if (duration == 0 || clock > duration) {
			return false;
		}
		return true;
	}

	public void launchBullet(Vec2f position, float distance, double angle) {
		this.position.x = position.x;
		this.position.y = position.y;

		bulletSprite.setImage(0);
		bulletSprite.setVisible(true);
		bulletSprite.setRotation(angle);
		velocity.y = 0;
		velocity.x = BULLETSPEED;
		velocity = velocity.getVector(angle);
		clock = 0;
		duration = distance / BULLETSPEED;
		this.position.x += velocity.x * 0.01F;
		this.position.y += velocity.y * 0.01F;
		bulletSprite.reposition(this.position.x + 0.5F, this.position.y + 0.5F);
	}

	@Override
	public void animate(float dt) {
		clock += dt;

		position.x += velocity.x * dt;
		position.y += velocity.y * dt;
		bulletSprite.reposition(position.x, position.y);
		if (clock > duration) {
			bulletSprite.setVisible(false);
		}
	}

	@Override
	public void setReversed(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReversed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAlive(boolean b) {
		duration=0;
	}

}
