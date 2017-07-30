package gameengine.actors.propertygen;

import gameengine.grid.interfaces.ActorGrid.ReadableGrid;
import util.Delay;

/**
 * Abstract class implementing the IActProperty interface to delineate the basic
 * functionality of a property that operates on a cycle or a delay between
 * actions. Classes extending this abstract class will only operate every given
 * number of "cycles" determined in the constructor.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic grid inherited by the class, defines basic necessary
 *            access permissions from within the grid
 */
public abstract class CycleProperty<G extends ReadableGrid> implements IActProperty<G> {

	private Delay myDelay;

	public CycleProperty(Integer myCycle) {
		myDelay = new Delay(myCycle);
	}

	/**
	 * uses the delay outlined in this class' constructor to return a boolean
	 * indicating whether the action for this IActProperty should be run on a
	 * given frame.
	 * 
	 * @see IActProperty#isOn()
	 */
	@Override
	public boolean isOn() {
		return myDelay.delayAction();
	}

}
