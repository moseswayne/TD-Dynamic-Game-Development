package factories;

import java.util.ArrayList;
import java.util.Arrays;

import gameengine.actors.MainActor;
import gameengine.actors.propertygen.IActProperty;
import util.VoogaException;

/**
 * Factory for creation of actors.
 * 
 * @author maddiebriere
 *
 */

public class ActorFactory extends AbstractFactory<MainActor>{

	private static final String PATH = "gameengine.actors.";
	private static final String HEALTH = "gameengine.actors.propertygen.HealthProperty";
	private static final String ERROR = "Reflection Error: No such property/actor";
	
	public ActorFactory() {
		super(PATH);
	}

	@Override
	protected MainActor failResponse() {
		try {
			throw new VoogaException(ERROR);
		} catch (VoogaException e) {
			//NO RESPONSE
		}
		return null;
	}
	
	//Get classes from data objects
	@Override
	protected Class<?>[] getClasses(Object... args) {
		//Convert Data objects to Property names
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(args[0].getClass());
		classes.add(args[1].getClass());
		classes.add(args[2].getClass());
		
		try {
			classes.add(Class.forName(HEALTH));
		} catch (ClassNotFoundException e1) {
			failResponse();
		}
		if(args.length-4>0){
			classes.add(IActProperty[].class); //add array of class
		}
		Class<?> [] toRet = new Class[args.length];
		classes.toArray(toRet);
		return toRet;
	}

	@Override
	protected String generateObjectType(String name) {
		return "MainActor";
	}

}
