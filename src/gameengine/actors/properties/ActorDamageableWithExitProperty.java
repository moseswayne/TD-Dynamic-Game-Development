package gameengine.actors.properties;

import java.util.function.Consumer;

import gamedata.composition.ActorDamageableWithExitData;
import gameengine.grid.interfaces.ActorGrid.ReadAndDamageGrid;

/**
 * Class extending the ActorDamageableProperty that allows for the alteration of
 * the game status' money and experience.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic grid that extends ReadAndDamageGrid
 */
public class ActorDamageableWithExitProperty<G extends ReadAndDamageGrid> extends ActorDamageableProperty<G> {

	public ActorDamageableWithExitProperty(ActorDamageableWithExitData myData) {
		super(myData);
	}

	/**
	 * overrides the method determining how the ActorDamageable determines
	 * action by adding on calls to alter the game status
	 */
	@Override
	protected void damageActor(G grid, Integer actorID, Consumer<Double> damage, Double health) {
		super.damageActor(grid, actorID, damage, health);
		grid.getWriteableGameStatus().addExperience(health);
		grid.getWriteableGameStatus().addMoney(health.intValue());
	}

}
