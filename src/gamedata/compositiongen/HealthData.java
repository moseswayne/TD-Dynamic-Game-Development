package gamedata.compositiongen;

import gamedata.ActorData;

/**
 * Defines Data objects unique to the health of 
 * an actor.
 * 
 * @author maddiebriere
 *
 */

public abstract class HealthData extends Data {
	
	@Override
	public void addData(ActorData actor){
		actor.setHealth(this);
	}
}
