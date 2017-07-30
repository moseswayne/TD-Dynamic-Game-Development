package gameengine.actors.properties;

import java.util.Collection;

import gamedata.composition.DamageInRadiusData;
import gameengine.actors.propertygen.CycleProperty;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.ReadAndDamageGrid;
import types.BasicActorType;

/**
 * Property class extending the CycleProperty abstract class that damages all
 * actors of a target BasicActorType within a given radius in set intervals.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 */
public class DamageInRadiusProperty<G extends ReadAndDamageGrid> extends CycleProperty<G> {

	private Double myRadius;
	private Double myDamage;
	private Collection<BasicActorType> myTargets;

	public DamageInRadiusProperty(DamageInRadiusData myData) {
		super(myData.getDamageRate());
		myRadius = myData.getRadius();
		myDamage = myData.getDamage();
		myTargets = myData.getTargets();
	}

	/**
	 * Method to damage enemies in a radius
	 * 
	 * @see IActProperty#action(gameengine.grid.interfaces.ActorGrid.ReadableGrid,
	 *      Integer)
	 */
	@Override
	public void action(G grid, Integer actorID) {
		myTargets.stream()
				.forEach(target -> grid
						.getActorDamagablesInRadius(grid.getLocationOf(actorID).getX(),
								grid.getLocationOf(actorID).getY(), myRadius, target)
						.forEach((damage, health) -> damage.accept(myDamage)));
	}

}
