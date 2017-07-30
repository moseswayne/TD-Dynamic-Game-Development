package gamedata.composition;

import gamedata.compositiongen.ShootTargetLineData;
import types.BasicActorType;
/**
 * Target only nearby actors when shooting
 * 
 * @author maddiebriere
 *
 */

public class ShootTargetNearData extends ShootTargetLineData{
	
	public ShootTargetNearData(){
		super();
	}
	
	public ShootTargetNearData(Double myRange, Integer fireRate, BasicActorType type, Integer projectile, Double speed){
		super(myRange, fireRate, type, projectile, speed);
	}
}
