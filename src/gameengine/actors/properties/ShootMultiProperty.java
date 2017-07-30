package gameengine.actors.properties;

import java.util.ArrayList;
import java.util.Collection;

import gamedata.composition.ShootMultiData;
import gameengine.grid.interfaces.ActorGrid.ReadAndSpawnGrid;
import gameengine.grid.interfaces.Identifiers.Grid2D;

/**
 * Shoot property class extending the ShootTargetLineProperty abstract class
 * that spawns a given number of actors at each fire with equal angles.
 * 
 * @author Moses Wayne
 *
 * @param <G> generic grid extending the ReadAndSpawnGrid
 */
public class ShootMultiProperty<G extends ReadAndSpawnGrid> extends ShootTargetLineProperty<G> {

	private Collection<Double> shotAngles;

	public ShootMultiProperty(ShootMultiData myData) {
		super(myData);
		shotAngles = new ArrayList<>();
		for (int shot = 0; shot < myData.getNumShots(); shot++) {
			shotAngles.add(shot * 2 * (Math.PI / myData.getNumShots()));
		}
	}

	/**
	 * Determines multiple angles on which to fire based on the number of
	 * targets
	 */
	@Override
	protected Collection<Double> getEnemyToShoot(Collection<Grid2D> points, Grid2D myPos) {
		ArrayList<Double> retCollection = new ArrayList<>();
		if (points.size() > 0) {
			shotAngles.stream().forEach((angle) -> retCollection.add(angle));
		}
		return retCollection;
	}

}
