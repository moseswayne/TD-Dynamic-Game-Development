package gameengine.grid.interfaces.ActorGrid;

import java.util.Collection;
import java.util.function.Consumer;

import gameengine.actors.propertygen.IActProperty;

/**
 *
 * @author Gideon
 *
 * This interface is designed to be used by actors that have spawning capabilities.
 * It implements ReadabaleGrid, so the actors can still find out where they are and it provides one
 * method which allows the user to spawn other actors;
 */
public interface ReadAndSpawnGrid extends ReadableGrid{

	/**
	 * 
	 * @param actorType the integer key to spawn the actor based off the actors key in GameData
	 * @param startX the start x location for the actor being spawned
	 * @param startY the start y location for the actor being spawned
	 * @return the IActProperty Consumer to add additional properties to the actor you just spawned
	 * call Consumer.accept(IActProperty propertyToAdd) for each new property you would like to add
	 * to the spawned actor.
	 */
	void actorSpawnActor(Integer actorType, double startX, double startY, Consumer<Collection<IActProperty<MasterGrid>>> action);
	
}
