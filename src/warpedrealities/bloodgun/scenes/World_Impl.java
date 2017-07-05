package warpedrealities.bloodgun.scenes;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.blood.ParticleManager;
import warpedrealities.bloodgun.scenes.gibs.GibHandler;

public class World_Impl implements World {

	private List<Actor> actors;
	private GibHandler gibHandler;
	private ParticleManager particleManager;
	
	public World_Impl(List<Actor> actors, GibHandler gibHandler, ParticleManager particleManager) {
		this.actors=actors;
		this.gibHandler=gibHandler;
		this.particleManager=particleManager;
	}

	@Override
	public List<Actor> getActors() {

		return actors;
	}

	@Override
	public GibHandler getGibHandler() {

		return gibHandler;
	}

	@Override
	public ParticleManager getParticleManager() {
		return particleManager;
	}

}
