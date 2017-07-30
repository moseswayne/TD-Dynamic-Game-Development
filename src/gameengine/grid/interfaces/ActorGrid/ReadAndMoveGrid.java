package gameengine.grid.interfaces.ActorGrid;

/**
 * 
 * @author Gideon
 *
 * Allows for actors to Move based off of an ID and a new location to move to.
 * Using ReadabaleGrid, you can get a location from an ID, then, in combination
 * with this interface, you can assign a new location to the actor with that given ID
 * based off of whatever movement pattern the actor has. 
 */
public interface ReadAndMoveGrid extends ReadableGrid{

	/**
	 * @param ID the ID of the actor to move
	 * @param newX the new X location after moving
	 * @param newY the new Y location after moving
	 */
	void move(int ID, double newX, double newY);
	
}
