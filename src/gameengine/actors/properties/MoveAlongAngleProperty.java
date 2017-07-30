package gameengine.actors.properties;

import gameengine.grid.interfaces.ActorGrid.MasterGrid;

/**
 * Class extending the abstract class MoveWithRangeProperty that moves an actor
 * with a specific range along a given angle at a given velocity
 * 
 * @author Moses Wayne
 *
 * @param <G>
 */
public class MoveAlongAngleProperty<G extends MasterGrid> extends MoveWithRangeProperty<G> {

	private double myPathAngle;

	public MoveAlongAngleProperty(double angle, double range, double speed) {
		super(range, speed);
		myPathAngle = angle;
	}

	/**
	 * Method that moves a projectile along an angle
	 */
	@Override
	protected void moveProj(G grid, Integer actorID, double speed) {
		xyDist(grid, actorID, speed, myPathAngle);
	}

}
