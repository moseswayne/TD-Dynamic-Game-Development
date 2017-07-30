package factories;

import gameengine.actors.properties.LimitedHealthProperty;
import gameengine.actors.propertygen.Property;
/**
 * Property factory, used in creation of Actors.
 * 
 * @author maddiebriere
 */


public class PropertyFactory extends AbstractFactory <Property> {
	private static final String PATH = "gameengine.actors.properties.";
	
	public PropertyFactory() {
		super(PATH);
	}

	@Override
	protected String generateObjectType(String name) {
		return name;
	}

	@Override
	protected Property failResponse() {
		return new LimitedHealthProperty(0.0);
	}


}
