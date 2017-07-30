package util;

import java.util.ResourceBundle;

/**
 * Used for reading property files
 * 
 * @author maddiebriere
 *
 */

public class PropertyUtil {
	
	public static String getTerm(String resource, String key) {
		ResourceBundle resources = ResourceBundle.getBundle(resource);
		String target;
		try {
			target = resources.getString(key);
		} catch (Exception e) {
			return "";
		}
		return target;
	}
}
