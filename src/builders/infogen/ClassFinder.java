package builders.infogen;

import util.PropertyUtil;

/**
 * Access a property file holding all of the full classpaths 
 * for different types of classes (e.g., Data, Conditions, etc.) 
 * 
 * @author maddiebriere
 *
 */

public class ClassFinder {
	private static final String CLASSES = "builders/resources/classpaths";
	
	/**
	 * Get the full class path for the given key.
	 * 
	 * For example:
	 * Input: "Data"
	 * Return: "gamedata.composition"
	 * 
	 * @param key String with which to search property file 
	 * @return Full class path
	 */
	public static String getClass(String key){
		return PropertyUtil.getTerm(CLASSES, key);
	}
}
