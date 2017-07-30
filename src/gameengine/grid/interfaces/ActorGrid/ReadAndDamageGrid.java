package gameengine.grid.interfaces.ActorGrid;

import java.util.Map;
import java.util.function.Consumer;

import gamestatus.WriteableGameStatus;
import types.BasicActorType;

/**
 * 
 * @author Gideon
 * 
 * The point of this grid is to allow actors to damage other actors.
 * Actors can both get a unique damagable based off of an ID passed in
 * as well as getting a collection of damagables using locational and ActorType filtering.
 * For example, a projectile could get the damagables for enemies within its radius
 * and by calling consumer.accept(double damageToDeal), they could deal damage to those actors. 
 */
public interface ReadAndDamageGrid extends ReadableGrid{
	
	/**
	 * @param x the x radius for the radial search (center point for search)
	 * @param y the y radius for the radial search (center point for search)
	 * @param radius the length of the radius to search from
	 * @param type the Type of Actor to filter the search on
	 * @return A Collection of consumers to apply damage to the filtered actors (consumer.accept(double damageToDeal))
	 */
	Map<Consumer<Double>, Double>getActorDamagablesInRadius(double x, double y, 
			double radius, BasicActorType type);
	
	/**
	 * @param actorID the actor ID to get the damagable consumer for
	 * @return the consumer which can be used to apply damage to the actor 
	 * with the specified ID (consumer.accept(double damageToDeal))
	 */
	Consumer<Double> getMyDamageable(int actorID);
	
	/**
	 * @return the WriteableGameStatus associated with the grid instance such that actors can edit the
	 * status of the game. For example, dying and adding xp to to WriteableGameStatus
	 */
	WriteableGameStatus getWriteableGameStatus();

}
