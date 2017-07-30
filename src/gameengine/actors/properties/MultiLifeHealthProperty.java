package gameengine.actors.properties;

import gamedata.composition.MultiLifeHealthData;

/**
 * Subclass of the LimitedHealthProperty that allows for an actor to have
 * multiple lives, reseting its own limited health until these lives run out
 * 
 * @author Moses Wayne
 *
 */
public class MultiLifeHealthProperty extends LimitedHealthProperty {

	private Integer myLives;

	public MultiLifeHealthProperty(MultiLifeHealthData data) {
		super(data.getStartHealth());
		myLives = data.getLives();
	}

	/**
	 * overrides its parent implementation of solely applying damage to allow
	 * for the reinstatement of health at the cost of a life
	 */
	@Override
	public void apply(double damage) {
		double fullHealth = super.getRemaining() / super.getPercent();
		super.apply(damage);
		if (myLives > 0 && !super.isAlive()) {
			super.apply(-1 * (fullHealth));
			myLives--;
		}
	}

}
