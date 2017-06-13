package warpedrealities.bloodgun.director;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.NPC;
import warpedrealities.bloodgun.actors.AI.Controller;
import warpedrealities.bloodgun.actors.AI.Ninja_Controller;
import warpedrealities.bloodgun.actors.impl.Ninja_NPC;
import warpedrealities.bloodgun.actors.impl.Player;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler;
import warpedrealities.bloodgun.collisionHandling.CollisionHandler_Impl;
import warpedrealities.bloodgun.level.Tile.Collision;
import warpedrealities.bloodgun.rendering.Platformer_Renderer;
import warpedrealities.core.core.GameManager;
import warpedrealities.core.rendering.Sprite;
import warpedrealities.core.rendering.Square_Int;
import warpedrealities.core.shared.Vec2f;

public class Director implements Director_Int {

	private static Director_Int director_Int;
	private double totalTime;
	private double interval;
	private double spawnClock;
	private int ninjasSpawned;
	private Platformer_Renderer renderer;
	private CollisionHandler collisionHandler;
	private List<Actor> actorList;
	private Queue<NPC> spawnQueue;

	public Director(Platformer_Renderer renderer, CollisionHandler collisionHandler, List<Actor> actorList) {
		this.renderer = renderer;
		this.collisionHandler = collisionHandler;
		this.actorList = actorList;
		spawnQueue = new LinkedList<NPC>();
		totalTime = 0;
		ninjasSpawned = 0;
		interval = 5;
		director_Int=this;

	}

	public static Director_Int getInterface()
	{
		return director_Int;
	}
	
	public void update(float dt) {
		totalTime += dt;
		if (interval > 0 && ninjasSpawned == 0) {
			interval -= dt;
			if (interval < 0) {
				spawnNinjas();
			}
		}
		if (!spawnQueue.isEmpty()) {
			spawnClock += dt;
			if (spawnClock > 0.5F) {
				activateNinja();
				spawnClock = 0;
			}
		}

	}

	private void activateNinja() {
		NPC ninja = spawnQueue.remove();
		float playerPos = actorList.get(0).getPosition().x;
		if (playerPos>ninja.getPosition().x && playerPos - 12 < ninja.getPosition().x) {
			int x = getSpawnLocation(true);
			int y = getSpawnHeight(x);
			ninja.setPosition(x, y);
		}
		if (playerPos<ninja.getPosition().x && playerPos + 12 > ninja.getPosition().x) {
			int x = getSpawnLocation(false);
			int y = getSpawnHeight(x);
			ninja.setPosition(x, y);
		}
		ninja.activate();
		actorList.add(ninja);
	}

	private void spawnNinjas() {
		boolean backAttack = false;
		if (actorList.get(0).getPosition().x > 20) {
			backAttack = true;
		}
		int ninjasPerWave = 10 + ((int) totalTime / 30);
		for (int i = 0; i < ninjasPerWave; i++) {
			spawnNinja(backAttack);
		}

		interval = 5;
	}

	private int getSpawnLocation(boolean backAttack) {
		// needs to be 16 tiles away
		int r = GameManager.getRandom().nextInt(4);
		int x = (int) actorList.get(0).getPosition().x;
		if (backAttack) {
			x -= 16 + r;
		} else {
			x += 16 + r;
		}
		return x;
	}

	private int getSpawnHeight(int x) {
		int y = GameManager.getRandom().nextInt(14) + 1;
		while (collisionHandler.getWorldCollision(x, y) == Collision.SOLID) {
			y = GameManager.getRandom().nextInt(14) + 1;
		}
		return y;
	}

	private Ninja_NPC generateNinja(Vec2f p) {
		Controller controller = new Ninja_Controller();
		controller.setTarget((Player)actorList.get(0));
		Sprite sprite = new Sprite(p, 1.0F, 64);
		Sprite swordSprite = new Sprite(new Vec2f(0, 0), 1.0F, 64);
		renderer.addSprite(swordSprite, "ninja.png");
		Ninja_NPC npc = new Ninja_NPC(p, sprite, swordSprite, collisionHandler, this, controller);
		renderer.addSprite(sprite, "ninja.png");
		spawnQueue.add(npc);
		return npc;
	}

	private void spawnNinjaAhead() {
		int x = getSpawnLocation(false);
		int y = getSpawnHeight(x);

		generateNinja(new Vec2f(x, y));
		ninjasSpawned++;

	}

	private void spawnNinjaBehind() {
		int x = getSpawnLocation(true);
		int y = getSpawnHeight(x);
		generateNinja(new Vec2f(x, y));

		ninjasSpawned++;

	}

	private void spawnNinja(boolean backAttack) {
		if (!backAttack) {
			spawnNinjaAhead();
		} else {
			if (GameManager.getRandom().nextBoolean()) {
				spawnNinjaAhead();
			} else {
				spawnNinjaBehind();
			}
		}
	}

	@Override
	public void decrementNinjas() {
		ninjasSpawned--;
	}

}
