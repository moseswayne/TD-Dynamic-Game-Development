package XML.directorytree.interfaces;

/**
 * @author Gideon
 *
 * This interface was made to provide a more substantial
 * alternative to just passing around a boolean. The switch can be
 * set to true, false, and its state can be read. See {@link ConcreteBoolSwitch)
 * for a concrete implementation. 
 */
public interface BoolSwitch {

	/**
	 * sets the switch status to true
	 */
	void setTrue();
	
	/**
	 * sets the switch status to false
	 */
	void setFalse();
	
	/**
	 * @return the current state of the switch
	 */
	boolean getState();
	
}
