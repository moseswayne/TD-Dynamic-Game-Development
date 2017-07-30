package gameengine.actors.properties;

import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.ReadAndDamageGrid;

/**
 * Property that adds experience to the game status from an actor. Because of
 * the format of the isOn() method, this class is primarily to be used as an
 * "exit" property, executing when the actor is removed from the screen.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic grid extending ReadAndDamageGrid
 */
public class AddExperienceProperty<G extends ReadAndDamageGrid> implements IActProperty<G> {

	private Double myExperience;

	/**
	 * NOTE: The FrontEnd, GameData, and Authoring Environment subteams did not
	 * implement this property into the game, so no data object was ever written
	 * for it. That is the reason for the empty constructor.
	 */
	public AddExperienceProperty() {

	}

	@Override
	public void action(G grid, Integer actorID) {
		grid.getWriteableGameStatus().addExperience(myExperience);
	}

	@Override
	public boolean isOn() {
		return false;
	}

}
