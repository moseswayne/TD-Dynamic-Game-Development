package builders.infogen;

import builders.infogen.masterpiece.HierarchyInfoGenerator;

/**
 * Generate information about Conditions, including
 * concrete subclasses, abstract super classes, etc.
 * (inherited).
 * 
 * @author maddiebriere
 *
 */

public class ConditionInfoGenerator extends HierarchyInfoGenerator{
	//keys for accessing class path property file
	private static final String CONDITION = "Condition";
	private static final String SUPER_CONDITION = "GenCondition";
	
	public ConditionInfoGenerator() {
		super(ClassFinder.getClass(CONDITION), ClassFinder.getClass(SUPER_CONDITION));
	}
	
	@Override
	public String simplifyName(Class<?> className) {
		return className.getSimpleName().replace(CONDITION, "");
	}

	@Override
	public Class<?> rebuildFromName(String name) {
		try {
			return Class.forName(name + CONDITION);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	@Override
	protected String rebuildAddress(String name){
		return ClassFinder.getClass(CONDITION) + "." + name + CONDITION;
	}
	

}