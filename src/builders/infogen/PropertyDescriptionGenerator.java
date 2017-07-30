package builders.infogen;

import builders.infogen.masterpiece.DataInfoGenerator;
/**
 * Generate property descriptions for display in front end!
 * 
 * @author maddiebriere
 *
 */

public class PropertyDescriptionGenerator {
	private static final String DESCRIPTIONS = "PropertyDescriptions";
	
	public static String getDescription(String propertyName){
		return DescriptionGenerator.getDescription(propertyName, ClassFinder.getClass(DESCRIPTIONS),
				new DataInfoGenerator());
	}
}
