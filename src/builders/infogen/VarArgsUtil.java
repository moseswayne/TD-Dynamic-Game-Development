package builders.infogen;

/**
 * Used to create var args arrays
 * 
 * @author maddiebriere
 *
 */

public class VarArgsUtil {
	
	/**
	 * Create a single array of Strings for the given String and 
	 * variable args array of Strings.
	 * 
	 * @param s1 Single string to add
	 * @param s2 Array of strings to add
	 * @return Array containing s1 and s2
	 */
	public static String[] createVarArgs(String s1, String ... s2){
		String [] toRet = new String[s2.length+1];
		toRet[0] = s1;
		for(int i=0; i<s2.length; i++){
			toRet[i+1] = s2[i];
		}
		return toRet;
	}
}
