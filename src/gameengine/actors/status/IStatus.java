package gameengine.actors.status;

import gamedata.compositiongen.StatusData;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.ReadableGrid;
import types.BasicActorType;

/**
 * Abstract class implementing the IActProperty interface outlining the basic
 * functionality of properties that inflict temporary "status conditions" to
 * actors.
 * 
 * @author Moses Wayne
 *
 * @param <G> generic grid extending the ReadableGrid
 */
public abstract class IStatus<G extends ReadableGrid> implements IActProperty<G> {

	private BasicActorType myTarget;
	private StatusDuration myLife;

	public IStatus(StatusData data) {
		myTarget = data.getMyTarget();
		myLife = data.getMyLife();
	}

	protected BasicActorType getMyTarget() {
		return myTarget;
	}

	@Override
	public boolean isOn() {
		return myLife.stillOn();
	}

}
