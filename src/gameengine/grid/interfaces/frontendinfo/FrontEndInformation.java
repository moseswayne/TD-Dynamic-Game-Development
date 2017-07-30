package gameengine.grid.interfaces.frontendinfo;

import gameengine.grid.interfaces.Identifiers.Grid2D;
/**
 * 
 * @author Gideon
 * This class is designed to provide all necessary information for the front end to display.
 * As of now, it implements getting the health of every actor, the option of the actor which
 * maps back to the actor data it was generated from, and the location of the actor in the form of a Grid2D.
 * It is expected that this interface be used in the form of a map where this interface is the value
 * and the key is the actor ID
 */

public interface FrontEndInformation {
	
	/**
	 * @return the Grid2D location of the actor being queried about
	 */
	Grid2D getActorLocation();
	
	/**
	 * @return the percent health of the actor as found in the IActor interface {@link src.gamengine.actors.management.Actor#getPercentHealth()}
	 */
	double getActorPercentHealth();
	
	/**
	 * @return the option identifier of the actor as found in the IActor interface {@link src.gamengine.actors.management.Actor#getgetMyOption()}
	 */
	int getActorOption();

}
