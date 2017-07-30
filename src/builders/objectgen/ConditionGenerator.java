package builders.objectgen;

import factories.ConditionFactory;
import gameengine.conditionsgen.Condition;
/**
 * Generate a Condition given a String representing the 
 * type of Condition and a list of arguments to pass to the 
 * constructor.
 * 
 * @author maddiebriere
 *
 */
public class ConditionGenerator {
	private static final String CONDITION = "Condition";
	
	public static Condition makeCondition(String key, Object ... args){
		ConditionFactory factory = new ConditionFactory();
		Condition toRet = factory.make(key + CONDITION, args);
		return toRet;
	}
	
}
