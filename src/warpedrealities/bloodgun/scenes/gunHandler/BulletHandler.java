package warpedrealities.bloodgun.scenes.gunHandler;

import warpedrealities.bloodgun.rendering.Platformer_Renderer;
import warpedrealities.core.rendering.Sprite_Rotatable;
import warpedrealities.core.shared.Vec2f;

public class BulletHandler {

	Bullet[] bullets;
	int liveBullets;
	public BulletHandler(Platformer_Renderer renderer) {
		bullets = new Bullet[10];
		for (int i = 0; i < bullets.length; i++) {
			Sprite_Rotatable sprite = new Sprite_Rotatable(new Vec2f(0, 0), 1.0F, 1);
			bullets[i] = new Bullet(sprite);
			renderer.addSprite(sprite, "bullet.png");
		}

	}

	public void addBullet(Vec2f position, Double angle, float d) {
		for (int i = 0; i < bullets.length; i++) {
			if (!bullets[i].isAlive()) {
				bullets[i].launchBullet(position, d, angle);
				liveBullets++;
				break;
			}

		}
	}

	public void update(float dt) {
		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isAlive()) {
				bullets[i].animate(dt);
				if (!bullets[i].isAlive())
				{
					liveBullets--;
				}
			}
		}
	}

	public void removeBullets() {

		if (liveBullets>0)
		{
			for (int i=0;i<bullets.length;i++)
			{
				bullets[i].setAlive(false);
			}
			liveBullets=0;
		}
		
	}

}
