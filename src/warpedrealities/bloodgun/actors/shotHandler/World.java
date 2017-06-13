package warpedrealities.bloodgun.actors.shotHandler;

import java.util.List;

import warpedrealities.bloodgun.actors.Actor;
import warpedrealities.bloodgun.scenes.gibs.GibHandler;

public interface World {

	List <Actor> getActors();
	GibHandler getGibHandler();
}
