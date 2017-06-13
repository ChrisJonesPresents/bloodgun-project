package warpedrealities.bloodgun.actors.shotHandler.impl;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.impl.Corpse;
import warpedrealities.bloodgun.actors.impl.Ninja_NPC;
import warpedrealities.bloodgun.actors.shotHandler.ShotHandler;
import warpedrealities.bloodgun.actors.shotHandler.World;
import warpedrealities.bloodgun.director.Director;
import warpedrealities.bloodgun.director.Director_Int;
import warpedrealities.core.core.GameManager;
import warpedrealities.core.rendering.SpriteManager;
import warpedrealities.core.rendering.Sprite_Rotatable;
import warpedrealities.core.rendering.Square_Rotatable_Int;
import warpedrealities.core.shared.Vec2f;

public class NinjaShotHandler implements ShotHandler {

	private Ninja_NPC ninja;
	private Director_Int director;
	
	public NinjaShotHandler(Ninja_NPC ninja,Director_Int director)
	{
		this.director=director;
		this.ninja=ninja;
	}
	
	@Override
	public void handleShot(Vec2f position, Vec2f origin, SpriteManager manager, World world) {
		//remove ninja
		ninja.removeSprites();
		world.getActors().remove(ninja);
		//need to decrement the director
		Director.getInterface().decrementNinjas();
		
		//create corpse
		Sprite_Rotatable sprite[]=new Sprite_Rotatable[6];
		int r=GameManager.getRandom().nextInt(4);
		for (int i=0;i<6;i++)
		{
			sprite[i]=new Sprite_Rotatable(ninja.getPosition(),1.0F,64);
			sprite[i].setVisible(true);
			sprite[i].setImage(16+(8*i)+r);
			manager.addSprite(sprite[i], "ninja.png");
		}
		
		Actor actor=new Corpse(ninja.getCollisionHandler(),ninja.getPosition(),origin,sprite);
		
		world.getActors().add(actor);
	}

}
