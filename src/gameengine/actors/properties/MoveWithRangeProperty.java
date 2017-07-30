package gameengine.actors.properties;

import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.MasterGrid;
import util.PathUtil;

/**
 * Abstract class implementing the IActProperty interface that outlines the
 * basic functionality of a movement property whose range is limited to a
 * certain distance. This means that once an actor has moved a d=certain
 * distance from its origin, it is removed from the game.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic grid extending the MasterGrid interface
 */
public abstract class MoveWithRangeProperty<G extends MasterGrid> implements IActProperty<G> {

	private double myRange;
	private double mySpeed;
	private double distanceTraveled;

	public MoveWithRangeProperty(double range, double speed) {
		myRange = range;
		mySpeed = speed;
		distanceTraveled = 0;
	}

	/**
	 * Action that moves the actor until it has surpassed its maximum distance,
	 * at which point it is forcibly removed from the game
	 * 
	 * @see IActProperty#action(gameengine.grid.interfaces.ActorGrid.ReadableGrid,
	 *      Integer)
	 */
	@Override
	public void action(G grid, Integer actorID) {
		moveProj(grid, actorID, mySpeed);
		if ((distanceTraveled += mySpeed) > myRange) {
			grid.getMyDamageable(actorID).accept(Double.POSITIVE_INFINITY);
		}
	}

	/**
	 * Abstract method that determines the type of movement that is undergone by
	 * a specific actor in this class' extending subclasses
	 * 
	 * @param grid
	 *            generic grid determining the access rights of this property
	 * @param actorID
	 *            unique integer delineating the identity of a given actor
	 * @param speed
	 *            the absolute velocity at which this actor will move
	 */
	abstract protected void moveProj(G grid, Integer actorID, double speed);

	protected void xyDist(G grid, Integer actorID, double speed, double angle) {
		grid.move(actorID, grid.getLocationOf(actorID).getX() + (PathUtil.getXByAngle(angle) * speed),
				grid.getLocationOf(actorID).getY() + (PathUtil.getYByAngle(angle) * speed));

	}

	@Override
	public boolean isOn() {
		return true;
	}
}
