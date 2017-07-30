package gameengine.grid.interfaces.Identifiers;


/**
 * 
 * @author Gideon
 *
 * This interface is intended to do everything that the ActorLocator can do with 
 * the additional method to set a new location for the actor. This interface can be used by anyone needing 
 * an instance of an actor, a location which houses the actor, and a a method to change that location
 * to a new x and y coordinate. 
 */
public interface SettableActorLocator extends ActorLocator{
	
	/**
	 * 
	 * @param x the new x coordinate for the actor associated with the instance
	 * @param y the new y coordinate for the actor associated with the instance
	 */
	void setLocation(double x, double y);

}
