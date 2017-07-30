package gameengine.actors.properties;

import java.util.Comparator;

import gameengine.grid.interfaces.ActorGrid.MasterGrid;
import types.BasicActorType;
import util.PathUtil;

/**
 * Class extending the abstract class MoveWithRangeProperty that moves an actor
 * with a specific range toward the nearest target of the specific
 * BasicActorType specified in the constructor.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic grid extending the MasterGrid interface
 */
public class MoveWithHeatSeekProperty<G extends MasterGrid> extends MoveWithRangeProperty<G> {

	private BasicActorType myTarget;

	public MoveWithHeatSeekProperty(double range, double speed, BasicActorType target) {
		super(range, speed);
		myTarget = target;
	}

	/**
	 * Moves projectile toward the nearest target of specific BasicActorType,
	 * switching targets mid flight if a closer target appears
	 */
	@Override
	protected void moveProj(G grid, Integer actorID, double speed) {
		grid.getActorLocations(myTarget).stream()
				.min(Comparator.comparingDouble(point -> PathUtil.getDistance(grid.getLocationOf(actorID), point)))
				.ifPresent((point) -> {
					double pathAngle = PathUtil.getAngle(grid.getLocationOf(actorID), point);
					xyDist(grid, actorID, speed, pathAngle);
				});
	}
}
