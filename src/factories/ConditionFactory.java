package factories;

import gameengine.conditions.KillAllCondition;
import gameengine.conditionsgen.Condition;

/**
 * Factory for creating Conditions;
 * 
 * @author maddiebriere
 *
 */

public class ConditionFactory extends AbstractFactory<Condition>{
	private static final String PATH = "gameengine.conditions.";
	
	public ConditionFactory() {
		super(PATH);
	}

	@Override
	protected String generateObjectType(String name) {
		return name;
	}

	@Override
	protected Condition failResponse() {
		return new KillAllCondition();
	}

}
