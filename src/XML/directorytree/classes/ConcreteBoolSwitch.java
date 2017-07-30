package XML.directorytree.classes;

import XML.directorytree.interfaces.BoolSwitch;

/**
 * 
 * @author Gideon
 *
 * This class is a concrete implementation of {@link BoolSwitch}
 * 
 * It is intended to keep track of a boolean and toggle it
 * 
 */
public class ConcreteBoolSwitch implements BoolSwitch{

	private boolean state;
	
	public ConcreteBoolSwitch(boolean startState){
		this.state = startState;
	}

	/**
	 * Sets the boolean to true
	 * 
	 * See {@link BoolSwitch}
	 */
	@Override
	public void setTrue() {
		state = true;
	}

	/**
	 * Sets the boolean to false
	 * 
	 * See {@link BoolSwitch}
	 */
	@Override
	public void setFalse() {
		state = false;
	}

	/**
	 * Returns the current state of the switch 
	 * 
	 * See {@link BoolSwitch}
	 */
	@Override
	public boolean getState() {
		return state;
	}
}

