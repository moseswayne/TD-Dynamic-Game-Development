package gameengine.actors.properties;

import gamedata.composition.AddMoneyCycleData;
import gameengine.actors.propertygen.CycleProperty;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.ReadAndDamageGrid;

/**
 * Property class extending the CycleProperty abstract class that adds money to
 * the GameStatus object in intervals. Can be used as an exit property, as the
 * isOn() method has no bearing on the exit of an Actor.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 */

public class AddMoneyCycleProperty<G extends ReadAndDamageGrid> extends CycleProperty<G> {

	private Double myMoney;

	public AddMoneyCycleProperty(AddMoneyCycleData myData) {
		super(myData.getFrequency());
		myMoney = myData.getMoney();
	}

	/**
	 * Adds money to the WritableGameStatus
	 * 
	 * @see IActProperty#action(gameengine.grid.interfaces.ActorGrid.ReadableGrid, Integer)
	 */
	@Override
	public void action(G grid, Integer actorID) {
		grid.getWriteableGameStatus().addMoney(myMoney.intValue());
	}

}
