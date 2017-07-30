package builders.objectgen;

import factories.DataFactory;
import gamedata.compositiongen.Data;

/**
 * For use in Authoring Environment.
 * 
 * Pass in the type of data (e.g "AfflictStatus") and the 
 * parameters for property, get back a Data object for storage in
 * the GameData object
 * 
 * @author maddiebriere
 *
 */

public class DataGenerator {

	public static Data makeData(String data, Object ... args){
		DataFactory factory = new DataFactory();
		Data toRet = factory.make(data, args);
		return toRet;
	}
}
