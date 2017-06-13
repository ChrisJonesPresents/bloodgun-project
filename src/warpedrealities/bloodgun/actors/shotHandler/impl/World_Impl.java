package warpedrealities.bloodgun.actors.shotHandler.impl;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.actors.shotHandler.World;
import warpedrealities.bloodgun.scenes.gibs.GibHandler;

public class World_Impl implements World {

	private List<Actor> actors;
	private GibHandler gibHandler;
	
	public World_Impl(List<Actor> actors, GibHandler gibHandler) {
		this.actors=actors;
		this.gibHandler=gibHandler;
	}

	@Override
	public List<Actor> getActors() {

		return actors;
	}

	@Override
	public GibHandler getGibHandler() {

		return gibHandler;
	}

}
