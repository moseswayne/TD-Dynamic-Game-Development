package gamedata.composition;

import gamedata.compositiongen.ShootData;
import types.BasicActorType;

/**
 * Allows the actor to shoot a "heat seeking" missile towards
 * any actor type.
 * 
 * @author maddiebriere
 *
 */

public class ShootHeatSeekingData extends ShootData {
	public ShootHeatSeekingData() {
		super();
	}

	public ShootHeatSeekingData(Double range, Integer rate, BasicActorType type, 
			Integer projectile, Double speed) {
		super(range, rate, type, projectile, speed);
	}
	
	
}
