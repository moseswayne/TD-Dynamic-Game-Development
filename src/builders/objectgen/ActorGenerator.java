package builders.objectgen;

import java.util.ArrayList;
import java.util.List;

import factories.ActorFactory;
import factories.PropertyFactory;
import gamedata.ActorData;
import gamedata.compositiongen.Data;
import gameengine.actors.MainActor;
import gameengine.actors.propertygen.IActProperty;
import gameengine.actors.propertygen.Property;
import util.IDGenerator;

/**
 * Builds an Actor using the ActorFactory. This should
 * be invoked by the GameController to generate new actors.
 * This class should be used AFTER the number representing the 
 * order has been used to invoke the correct ActorData from
 * the GameData object (using getOption(Integer index)) as 
 * its make function requires an IDGenerator-created ID and the
 * ActorData (like a blueprint) corresponding to the desired Actor.
 * 
 * @author maddiebriere
 */

public class ActorGenerator{
	private static final String DATA  = "Data";
	private static final String PROPERTY = "Property";

	/**
	 * Generate an Actor using an ID (generated from IdGenerator, 
	 * represents Grid placement) and ActorData object.
	 * 
	 * @param ID Represents grid placement/ ActorGrid index
	 * @param data ActorData holding information about how to make Actor
	 * @return Actor produced from factory
	 */
	public static MainActor makeActor(Integer option, ActorData data){
		return makeActor(option,IDGenerator.getNewID(),data);
	}
	
	/**
	 * Generate an Actor with the ID already given. 
	 * @param option Integer option to be passed into Actor
	 * @param ID Represents grid placement/ ActorGrid index
	 * @param data ActorData holding information about how to make Actor
	 * @return Actor produced from factory
	 */
	public static MainActor makeActor(Integer option, int id, ActorData data) {
		ActorFactory actorFactory = new ActorFactory();
		ArrayList<Object> toBuild = new ArrayList<Object>();
		List<Data> properties = data.getMyData();
		PropertyFactory propFactory = new PropertyFactory();
		
		toBuild.add(data.getType()); //add type
		toBuild.add(option);
		toBuild.add(id); //add ID
		Property health = propFactory.make(data.getHealth().
				getClass().getSimpleName().replace(DATA, PROPERTY), data.getHealth());
		toBuild.add(health);
		IActProperty<?>[] extras = new IActProperty[data.getMyData().size()];
		for(int i=0; i<properties.size(); i++){
			String dataName = properties.get(i).getClass().getSimpleName();
			String propertyName = dataName.replace(DATA, PROPERTY);
			Property property = propFactory.make(propertyName, properties.get(i));
			extras[i]=(IActProperty)property;
		}
		toBuild.add(extras);
		MainActor toRet = actorFactory.make(toBuild.toArray());
		return toRet;
	}
}
