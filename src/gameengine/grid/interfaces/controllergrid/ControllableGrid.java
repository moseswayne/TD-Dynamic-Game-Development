package gameengine.grid.interfaces.controllergrid;

import gameengine.actors.management.Actor;
import gameengine.grid.interfaces.Identifiers.Grid2D;
/**
 * 
 * @author Gideon
 *
 * This interface is designed to be used by the controller and 
 * has methods specifically added to allow for front end authoring to the grid.
 * Actors can be moved, added, found, removed, and checking for valid map locations can be used.
 * The interface also extends SteppableGrid which, when stepped, calls every actor 
 * on the grid's .act(...) method.
 */
public interface ControllableGrid extends Steppable{
	
	/**
	 * @param ID the ID of the actor that is being moved
	 * @param newX the new X location to move to
	 * @param newY the new Y location to move to
	 */
	void move(int ID, double newX, double newY);
	
	/**
	 * @param ID the ID of the actor that should be removed from the grid (it will then be removed)
	 */
	void removeActor(int ID);
	
	/**
	 * @param actor the actor being spawned into the grid
	 * @param startX the start x locaiton of the actor
	 * @param startY the start y location of the actor
	 */
	void controllerSpawnActor(Actor actor, double startX, double startY);
	
	/**
	 * @param ID the ID of the actor whose location is being queried upon
	 * @return the Grid2D location of the actor with the corresponding ID
	 */
	Grid2D getLocationOf(int ID);
	
	/**
	 * @param x the x location being queried upon
	 * @param y the y location being queried upon
	 * @return true if the location is valid, false otherwise
	 */
	boolean isValidLoc(double x, double y);
	
}
