package gamedata.compositiongen;

import types.BasicActorType;

/**
 * Subset of ShootData defining specifically the situation of shooting in a 
 * straight, targeted line
 * 
 * @author maddiebriere
 *
 */

public abstract class ShootTargetLineData extends ShootData {

	public ShootTargetLineData() {
		super();
	}

	public ShootTargetLineData(Double range, Integer rate, BasicActorType type, Integer projectile, Double speed) {
		super(range, rate, type, projectile, speed);
	}
	
}
