package gameengine.actors.propertygen;

import gameengine.grid.interfaces.ActorGrid.ReadableGrid;

/**
 * Interface defining the basic unit of action of an actor in the GameEngine.
 * Each IActProperty performs a specific, self-contained action that relies only
 * on the grid that is provided to it to perform an action, and the acotrID
 * given to it to identify its actor.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic defining a specific grid that is used by a given
 *            implementing class. The generic class for the interface must
 *            extend the ReadableGrid interface, the lowest possible level of
 *            readable information provided to a property.
 */
public interface IActProperty<G extends ReadableGrid> extends Property {

	/**
	 * Primary method for an IActProeprty. The method takes a grid and an
	 * actorID as parameters to perform an action on the actor in the game. The
	 * action relies on the grid it inherits to read and write information to
	 * the game, based on access permissions delineated by an implementing
	 * class.
	 * 
	 * @param grid
	 *            generic grid initialized in a given IActProperty class. used
	 *            to interface with the rest of the game components
	 * @param actorID
	 *            unique integer identifying the actor to which this property
	 *            belongs (is held within).
	 */
	public void action(G grid, Integer actorID);

	/**
	 * Boolean method that determines whether a given IActProperty is meant to
	 * be called. This is a useful method to modify for IActProperty classes
	 * that should not be run every frame.
	 * 
	 * @return boolean indicating whether the action() method should be called
	 */
	public boolean isOn();

}
