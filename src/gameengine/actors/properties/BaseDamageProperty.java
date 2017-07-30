package gameengine.actors.properties;

import java.util.List;

import gamedata.composition.BaseDamageData;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.ReadAndDamageGrid;
import types.BasicActorType;

/**
 * Property ties the damage accepted for an actor to the lives of the player.
 * This property, in essence, allows the player to lose lives when enemy units
 * reach the actor's radius, removing the enemies from the screen while taking
 * damage.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            grid implementation that extends the ReadAndDamageGrid
 */
public class BaseDamageProperty<G extends ReadAndDamageGrid> implements IActProperty<G> {

	private double myRadius;
	private List<BasicActorType> myTargets;

	public BaseDamageProperty(BaseDamageData data) {
		myRadius = data.getMyRadius();
		myTargets = data.getMyTargets();
	}

	/**
	 * Hurts enemy actor and applies damage to game status
	 * 
	 * @see IActProperty#action(gameengine.grid.interfaces.ActorGrid.ReadableGrid,
	 *      Integer)
	 */
	@Override
	public void action(G grid, Integer actorID) {
		myTargets.stream()
				.forEach(
						target -> grid
								.getActorDamagablesInRadius(grid.getLocationOf(actorID).getX(),
										grid.getLocationOf(actorID).getY(), myRadius, target)
								.forEach((damage, remaining) -> {
									damage.accept(remaining);
									grid.getWriteableGameStatus().loseLife();
								}));
	}

	@Override
	public boolean isOn() {
		return true;
	}

}
