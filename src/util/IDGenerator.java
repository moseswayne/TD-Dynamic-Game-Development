package util;

import java.util.function.Supplier;

/**
 * @author sarahzhou
 *	Generates unique ids for actors so that front end actors can be referenced to corresponding back end actors
 */
public class IDGenerator {
	
	private static int currentID = 0;
	
	public static Supplier<Integer> getNextID() {
		return () -> getNewID();
	}
	
	public static int getNewID() {
		return currentID++;
	}

}
