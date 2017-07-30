package gamedata.compositiongen;

import gamedata.ActorData;

/**
 * Data objects are for use in the front end and in creation of actors.
 * These data objects define the variables needed to create corresponding
 * properties in Actors, but encapsulate the behavior. Hence, the frontend has 
 * no access to any of the functionality -- it is only able to set data.
 * 
 * @author maddiebriere
 *
 */

public abstract class Data{
	
	/**
	 * Define how to add this data object to the given
	 * ActorData (e.g., will it be added directly to its
	 * list of data objects or will it be instantiated 
	 * separately)
	 * 
	 * @param data ActorData to which to add this Data object
	 */
	public abstract void addData(ActorData data);
	
	/**
	 * Overrides the equals method to allow for comparison of
	 * Data object via class name.
	 * 
	 * @param obj Object to compare
	 * @return true if equal (same class name), false otherwise
	 */
	@Override
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof Data)){
			return false;
		}
		Data data = (Data)obj;
		return data.getClass().equals(this.getClass());
	}
}