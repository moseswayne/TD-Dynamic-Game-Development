package builders.infogen;

import builders.infogen.masterpiece.HierarchyInfoGenerator;
import util.PropertyUtil;
/**
 * Generate descriptions
 * @author maddiebriere
 *
 */

public class DescriptionGenerator {
	
	/**
	 * Returns a String defining the property. If there is no
	 * String for the specific property, if will look for a description
	 * for supertypes.
	 * 
	 * Pass this a propertyName, WITHOUT THE WORD "DATA"
	 * appended to the end.
	 * @param propertyName Property name (e.g., ShootMulti)
	 * @return description A String defining the property
	 */
	public static String getDescription(String propertyName, String file, 
			HierarchyInfoGenerator info){
		String toRet = PropertyUtil.getTerm(file, propertyName);
		Class<?> superclass = null;
		int counter = 0; //to stop time-outs
		while(toRet.equals("") && !Object.class.equals(superclass)){
			try{
				counter++;
				Class<?> clzz = info.rebuildFromName(propertyName);
				superclass = clzz.getSuperclass();
				String name =info.simplifyName(superclass);
				toRet = PropertyUtil.getTerm(file, name);
			}catch(Exception e){
				return toRet;
			}
		}
		return toRet;
	}
}
