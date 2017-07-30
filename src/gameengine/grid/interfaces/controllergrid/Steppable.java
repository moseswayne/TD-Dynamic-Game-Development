package gameengine.grid.interfaces.controllergrid;

/**
 * 
 * @author Gideon
 *
 * A basic interface which implements nothing other than .step()
 * This interface could be implemented by any classes/interfaces that 
 * need a step function with no parameters
 */
public interface Steppable{
	
	/**
	 * Stepping moves the steppable object forward 1 step
	 * A concrete example being allowing all actors to act()
	 */
	void step();
	
}
