package gameengine.actors.properties;

import gamedata.composition.LimitedHealthData;
import gameengine.actors.propertygen.HealthProperty;

/**
 * Implementing class of the HealthProperty interface that specifies the
 * functionality of the health of an actor with a set amount of mutable health.
 * 
 * @author Moses Wayne
 *
 */
public class LimitedHealthProperty implements HealthProperty {

	private double myHealth;
	private double startHealth;

	public LimitedHealthProperty(LimitedHealthData data) {
		myHealth = startHealth = data.getStartHealth();
	}

	public LimitedHealthProperty(Double startHealth) {
		myHealth = this.startHealth = startHealth;
	}

	/**
	 * Method that subtracts the given damage from the current health
	 */
	@Override
	public void apply(double damage) {
		myHealth -= damage;
	}

	/**
	 * Method returning true if health remains and false otherwise
	 */
	@Override
	public boolean isAlive() {
		return myHealth > 0;
	}

	/**
	 * Method returning a double between 0 and 1 representing the percentage of
	 * health remaining for a given actor
	 */
	@Override
	public double getPercent() {
		return myHealth / startHealth;
	}

	/**
	 * Method returning the literal value of remaining health
	 */
	@Override
	public double getRemaining() {
		return myHealth;
	}

}
