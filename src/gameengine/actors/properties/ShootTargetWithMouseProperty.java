package gameengine.actors.properties;

import java.util.Arrays;
import java.util.Collection;

import gamedata.composition.ShootTargetWithMouseData;
import gameengine.grid.interfaces.ActorGrid.ReadAndSpawnGrid;

/**
 * Subclass of the ShootTargetFarProperty that determines the direction in which
 * to shoot from the location of the users mouse or cursor
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic grid extending the ReadAndSpawnGrid
 */
public class ShootTargetWithMouseProperty<G extends ReadAndSpawnGrid> extends ShootTargetFarProperty<G> {

	public ShootTargetWithMouseProperty(ShootTargetWithMouseData myData) {
		super(myData);
	}

	/**
	 * overrides primary action to deliver places to shoot from the event queue
	 * rather than the grid's method of radii enemies
	 */
	@Override
	public void action(G grid, Integer actorID) {
		Collection<Double> dirCoordinates = getEnemyToShoot(Arrays.asList(grid.getEventQueue().getLocation()),
				grid.getLocationOf(actorID));
		spawnProjectiles(grid, dirCoordinates, grid.getLocationOf(actorID));
	}

}
