package warpedrealities.bloodgun.scenes;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.blood.ParticleManager;
import warpedrealities.bloodgun.scenes.gibs.GibHandler;

public interface World {

	List <Actor> getActors();
	GibHandler getGibHandler();
	ParticleManager getParticleManager();
}
