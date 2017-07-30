package gameengine.actors.propertygen;

/**
 * Interface defining the component delineating the "health" of an actor, adding
 * to its composition. The HealthProperty is a required component of a MainActor
 * class, but the implementing classes of the HealthProperty interface can
 * effectively remove its presence (immunity, infinity, etc). Performs all
 * health related actions for the MainActor class such as damage application,
 * whether or not the specific actor is still active, and returning percent
 * health remaining and total health remaining.
 * 
 * @author Moses Wayne
 *
 */
public interface HealthProperty extends Property {

	/**
	 * Method to apply damage to the health property of a MainActor
	 * 
	 * @param damage
	 *            double representing the amount of damage to be applied
	 */
	public void apply(double damage);

	/**
	 * Boolean method to determine the current status of a given MainActor
	 * 
	 * @return boolean representing the current active status of an actor
	 */
	public boolean isAlive();

	/**
	 * Method to return the percentage of health still remaining in a given
	 * MainActor
	 * 
	 * @return double representing the percentage (number between 0 and 1) of
	 *         health still remaining in an actor
	 */
	public double getPercent();

	/**
	 * Method to return the literal amount of health still remaining in an actor
	 * 
	 * @return
	 */
	public double getRemaining();
}
