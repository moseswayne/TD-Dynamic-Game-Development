package gameengine.actors.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import gamedata.composition.ActorDamageableData;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.ReadAndDamageGrid;
import types.BasicActorType;

/**
 * Class implementing the IActProperty interface that allows for an actor to be
 * damaged within a certain radius called a "hitbox". This "hitbox" accepts
 * damage from all enemy actors within a certain radius, removing them and
 * damaging itself.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 */
public class ActorDamageableProperty<G extends ReadAndDamageGrid> implements IActProperty<G> {

	private Collection<BasicActorType> myTypes;
	private double hitBox;

	public ActorDamageableProperty(ActorDamageableData data) {
		hitBox = data.getMyHitRadius();
		myTypes = new ArrayList<>();
		for (BasicActorType t : data.getMyEnemyTypes()) {
			myTypes.add(t);
		}
	}

	/**
	 * Action to damage enemy units inside hitbox and to take the remaining
	 * damage
	 * 
	 * @see IActProperty#action(gameengine.grid.interfaces.ActorGrid.ReadableGrid,
	 *      Integer)
	 */
	public void action(G grid, Integer actorID) {
		myTypes.stream()
				.forEach(type -> grid
						.getActorDamagablesInRadius(grid.getLocationOf(actorID).getX(),
								grid.getLocationOf(actorID).getY(), hitBox, type)
						.forEach((damage, health) -> damageActor(grid, actorID, damage, health)));
	}

	/**
	 * Helper method to determine the action taken for each given target
	 * 
	 * @param grid
	 *            generic grid used to interface with the game engine
	 * @param actorID
	 *            unique integer identification of its actor
	 * @param damage
	 *            the consumer used to damage the target
	 * @param health
	 *            the remaining health of the target
	 */
	protected void damageActor(G grid, Integer actorID, Consumer<Double> damage, Double health) {
		grid.getMyDamageable(actorID).accept(health);
		damage.accept(health);
	}

	@Override
	public boolean isOn() {
		return true;
	}
}