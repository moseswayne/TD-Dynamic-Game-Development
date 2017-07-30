package gameengine.grid.classes;

import gameengine.actors.management.Actor;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import gameengine.grid.interfaces.Identifiers.SettableActorLocator;

/**
 * @author Gideon
 *
 * The point of this classes was to map every actor to a location.
 * It has getters for the locaiton and actor and setters for the
 * location because the actor shoul dnot change after instantiation.
 */

public class ActorFinder implements SettableActorLocator{
	
	private Grid2D location;
	private Actor actor;

	public ActorFinder(Grid2D location, Actor actor){
		this.actor = actor;
		this.location = location;
	}
	
	/**
	 * Returns the location of the actor
	 */
	@Override
	public Grid2D getLocation() {
		return location;
	}

	/**
	 * Sets the location of the actor
	 */
	@Override
	public void setLocation(double x, double y) {
		location = new Coordinates(x, y);
	}

	/**
	 * Gets the actor that the class was instanitated with
	 */
	@Override
	public Actor getActor() {
		return actor;
	}

}
