package gameengine.actors.propertygen;

import gameengine.grid.interfaces.ActorGrid.ReadAndSpawnGrid;

/**
 * Upgrade method to dictate when/how
 * an actor is updated. Uses read/spawn grid
 * in order to spawn updated actor.
 * 
 * @author maddiebriere
 *
 * @param <G>
 */

public abstract class UpgradeProperty<G extends ReadAndSpawnGrid> implements  IActProperty<G>{

	@Override
	public void action(G grid, Integer actorID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOn() {
		// TODO Auto-generated method stub
		return false;
	}

}
