package gameengine.grid.interfaces.Identifiers;

import gameengine.actors.management.Actor;

/**
 * 
 * @author Gideon
 *
 * This interface is intended to be used as an internal API for the grid. 
 * The interface allows for getters for both an Actor as well as a location.
 * It is intended to map every actor to their location on the grid. 
 */
public interface ActorLocator{

	/**
	 * @return the actor associated with the FindAble Actor interface
	 */
	Actor getActor();
	
	/**
	 * @return the location of the actor associated with the FindAble Actor interface
	 */
	Grid2D getLocation();
		
}
