package gamedata.composition;

import types.BasicActorType;

/**
 * Allows the user to shoot a target with a mouse
 * 
 * @author maddiebriere
 */

public class ShootTargetWithMouseData extends ShootTargetFarData{

	public ShootTargetWithMouseData(){
		super();
	}
	
	public ShootTargetWithMouseData(Double range, Integer rate, 
			BasicActorType type, Integer projectile, Double speed) {
		super(range, rate, type, projectile, speed);
	}
	
}
