package gameengine.grid.classes;

import gameengine.actors.management.Actor;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import gameengine.grid.interfaces.frontendinfo.FrontEndInformation;

/**
 * 
 * @author Gideon
 *
 * This class was made to store all of the information that the front end
 * will need when displaying the actors during game playing. This class
 * should be paired with a Map of <ID, DisplayInfo>. See concrete
 * implementation in ActorGrid.
 */
public class DisplayInfo implements FrontEndInformation{
	
	private Grid2D loc;
	private double healthRemaining;
	private int actorOption;
	
	public DisplayInfo(Grid2D loc, Actor actor){
		this.loc = loc;
		this.healthRemaining = actor.getPercentHealth();
		this.actorOption = actor.getMyOption();
	}

	/**
	 * returns the location of the actor
	 */
	@Override
	public Grid2D getActorLocation() {
		return loc;
	}

	/**
	 * returns the percent health of the actor
	 */
	@Override
	public double getActorPercentHealth() {
		return healthRemaining;
	}

	/**
	 * returns the option of the actor for when the actor was made in the factory
	 */
	@Override
	public int getActorOption() {
		return actorOption;
	}

}
