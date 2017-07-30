package gameengine.actors.properties;

import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.ReadAndMoveGrid;

/**
 * Abstract class implementation of the IActProperty interface that allows for
 * the movement of the actor based on controls dictated by the player.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic grid that extends the ReadAndMoveGrid interface
 */
public abstract class MoveAxisUserProperty<G extends ReadAndMoveGrid> implements IActProperty<G> {

	private Double mySensitivity;

	public MoveAxisUserProperty(Integer sensitivity) {
		mySensitivity = sensitivity / 1000.0;
	}

	/**
	 * Method that moves the actor
	 */
	@Override
	public void action(G grid, Integer actorID) {
		move(grid, actorID);
	}

	/**
	 * Determines movement based on abstract implementation of subclasses
	 * 
	 * @param grid
	 * @param actorID
	 */
	protected abstract void move(G grid, Integer actorID);

	protected double getKeyMoveX(G grid, Integer actorID) {
		return getKeyMovement(grid.getEventQueue().getLocation().getX(), grid.getLocationOf(actorID).getX());
	}

	protected double getKeyMoveY(G grid, Integer actorID) {
		return getKeyMovement(grid.getEventQueue().getLocation().getY(), grid.getLocationOf(actorID).getY());
	}

	private double getKeyMovement(double loc1, double loc2) {
		return (loc1 > loc2 ? mySensitivity : -1 * mySensitivity);
	}

	@Override
	public boolean isOn() {
		return true;
	}

}
