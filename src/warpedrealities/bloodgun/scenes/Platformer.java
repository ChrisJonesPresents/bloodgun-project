package warpedrealities.bloodgun.scenes;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.glfw.GLFW;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.impl.Player;
import warpedrealities.bloodgun.actors.shotHandler.impl.World_Impl;
import warpedrealities.bloodgun.actors.impl.Humanoid.AnimationState;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler_Impl;
import warpedrealities.bloodgun.director.Director;
import warpedrealities.bloodgun.level.Level;
import warpedrealities.bloodgun.rendering.Platformer_Renderer;
import warpedrealities.bloodgun.scenes.gibs.GibHandler;
import warpedrealities.bloodgun.scenes.gibs.impl.GibHandler_Impl;
import warpedrealities.bloodgun.scenes.gunHandler.BulletHandler;
import warpedrealities.bloodgun.scenes.gunHandler.GunHandler;
import warpedrealities.core.input.Keyboard;
import warpedrealities.core.input.MouseHook;
import warpedrealities.core.rendering.Sprite;
import warpedrealities.core.rendering.Sprite_Rotatable;
import warpedrealities.core.shared.SceneBase;
import warpedrealities.core.shared.Vec2f;

public class Platformer extends SceneBase {

	private Platformer_Renderer renderer;
	private List<Actor> actors;
	private Player player;
	private Level level;
	private CollisionHandler collisionHandler;
	private Platformer_MouseHandler mouseHandler;
	private Director director;
	private GunHandler gunHandler;
	private Sprite reticle;
	private GibHandler gibHandler;
	
	public Platformer() {
		renderer = new Platformer_Renderer();
		mouseHandler = new Platformer_MouseHandler();
		actors = new ArrayList<Actor>();
		newGame();
		
	}

	private void newGame() {
		// setup player

		level = new Level();
		collisionHandler = new CollisionHandler_Impl(level, actors);

		gibHandler=new GibHandler_Impl(renderer,collisionHandler);
		Sprite sprite = new Sprite(new Vec2f(-2, -2), 1.0F, 16);
		renderer.addSprite(sprite, "bloodgun.png");
		Sprite_Rotatable arm = new Sprite_Rotatable(new Vec2f(0, 0), 1.0F, 4);
		renderer.addSprite(arm, "arm.png");
		player = new Player(new Vec2f(5, 10), sprite, collisionHandler, arm);
		actors.add(player);
		director = new Director(renderer, collisionHandler, actors);

		reticle = new Sprite(new Vec2f(4, 4), 1.0F, 1);
		reticle.setVisible(true);
		renderer.addSprite(reticle, "reticle.png");
		gunHandler = new GunHandler(collisionHandler, new BulletHandler(renderer), renderer.getSpriteManager(),new World_Impl(actors,gibHandler));
	}

	@Override
	public void update(float dt) {

		director.update(dt);

		for (int i = 0; i < actors.size(); i++) {
			if (Math.abs(actors.get(i).getPosition().x-player.getPosition().x)>16 
					&& actors.get(i).removalNeeded())
			{
				actors.get(i).removeSprites();
				actors.remove(i);
			}
			else
			{
				actors.get(i).update(dt);	
			}

		}
		
		if (player.getAnimationState()==AnimationState.NONE)
		{
			renderer.centerCamera(player.getPosition().x, player.getPosition().y, level);			
			float x = renderer.getCameraPosition().x - MouseHook.getInstance().getPosition().x;
			float y = renderer.getCameraPosition().y - MouseHook.getInstance().getPosition().y;
			Double angle = mouseHandler.getMouseAngle(player.getPosition(), x, y);
			player.setGunAngle(angle);	
			gunHandler.update(dt, player.getPosition(), angle);			
		}
		else
		{
			gunHandler.removeBullets();
		}

		gibHandler.update(dt);

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		renderer.drawSetup(openGL_Variables);
		renderer.draw(openGL_Variables[2], openGL_Variables[0]);
	}

	@Override
	public void start(MouseHook mouse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		renderer.discard();
	}

}
