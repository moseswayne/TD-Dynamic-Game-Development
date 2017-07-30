package gameengine.grid.interfaces.ActorGrid;

import java.util.Collection;

import gameengine.grid.interfaces.Identifiers.Grid2D;
import types.BasicActorType;
import ui.player.listener.ListenQueue;

/**
 * @author Gideon
 * 
 * This interface is designed to allow for basic actor information
 * accessibility on the grid. It allows you to find Actor locations based
 * on ID, or ActorType. It also allows you to check if a location is a valid location.
 * This class is intended to be used by all of the actors when planning their next actions
 * without giving them the ability to edit anything on the grid. 
 */
public interface ReadableGrid{
	/**
	 * @param x the x coordinate location to start the radial search from
	 * @param y the y coordinate location to start the radial search from
	 * @param radius the radius length of the search 
	 * @param type the type of actors in the search radius to find
	 * @return A Collection of all actor locations given the search parameters
	 */
	Collection<Grid2D> getActorLocationsInRadius(double x, double y, double radius, BasicActorType type);
	
	/**
	 * @param id the id of an actor. Since actors don't have access to ID's other than their own, this should generally be the actor's own id. 
	 * @return the location of the actor that has the id queried about
	 */
	Grid2D getLocationOf(int id);
	
	/**
	 * @param type the ActorType to filter when getting locations
	 * @return a Collection of all locations of the specified ActorType on the Grid
	 */
	Collection<Grid2D> getActorLocations(BasicActorType type);
	
	/**
	 * @param x the x position of interest
	 * @param y the y position of interest
	 * @return whether or not that location on the grid is a valid actor location (for example obstacles could prevent this as could boundaries)
	 */
	boolean isValidLoc(double x, double y);
	
	/**
	 * @return the maximum x coordinate of the grid (0 - returnVal) are the x boundaries
	 */
	double getMaxX();
	
	/**
	 * @return the maximum y coordinate of the grid (0 - returnVal) are the y boundaries
	 */
	double getMaxY();
	
	/**
	 * @return the event queue which contains information
	 * about the keys being pressed such that the actors can listen to it
	 * and act accordingly
	 */
	ListenQueue getEventQueue();
}
