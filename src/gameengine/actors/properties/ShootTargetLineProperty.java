package gameengine.actors.properties;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import gamedata.compositiongen.ShootTargetLineData;
import gameengine.actors.propertygen.IActProperty;
import gameengine.actors.propertygen.ShootTargetProperty;
import gameengine.grid.interfaces.ActorGrid.MasterGrid;
import gameengine.grid.interfaces.ActorGrid.ReadAndSpawnGrid;

/**
 * Abstract class extension of the ShootTargetProperty abstract class, given to
 * each of the actors that are spawned a MoveAlongAngleProperty based on the
 * location of said actor and the speed and range passed to this class' methods
 * 
 * @author Moses Wayne
 *
 * @param <G> generic grid extending the ReadAndSpawnGrid
 */
public abstract class ShootTargetLineProperty<G extends ReadAndSpawnGrid> extends ShootTargetProperty<G> {

	public ShootTargetLineProperty(ShootTargetLineData myData) {
		super(myData);
	}

	/**
	 * Adds the MoveAlongAngleProperty to the actors
	 */
	@Override
	protected Consumer<Collection<IActProperty<MasterGrid>>> projectileProperty(Double target, double range,
			double speed) {
		return (list) -> list.addAll(Arrays.asList(new MoveAlongAngleProperty<MasterGrid>(target, range, speed)));
	}

}
