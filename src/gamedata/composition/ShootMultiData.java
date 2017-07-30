package gamedata.composition;

import gamedata.compositiongen.ShootTargetLineData;
import types.BasicActorType;

/**
 * 
 * Accommodates multiple shots at once
 * 
 * @author maddiebriere
 *
 */

public class ShootMultiData extends ShootTargetLineData {

	private Integer numShots;
	
	public ShootMultiData(){
		super();
		this.numShots = 0; //double shot is default
	}
	
	public ShootMultiData(Integer quantity, Double range, Integer rate, 
			BasicActorType type, Integer projectile, Double speed){
		super(range,rate,type,projectile,speed);
		this.numShots = quantity;
	}

	public Integer getNumShots() {
		return numShots;
	}

	public void setNumShots(Integer numShots) {
		this.numShots = numShots;
	}
}
