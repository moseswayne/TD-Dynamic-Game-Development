package gamedata.composition;

import gamedata.compositiongen.HealthData;

/**
 * Gives the actor a limited amount of health
 * 
 * @author maddiebriere
 *
 */

public class LimitedHealthData extends HealthData{
	
	private double startHealth;
	
	public LimitedHealthData(){
		this(10.0);
	}
	
	public LimitedHealthData(Double health){
		startHealth = health;
	}

	public double getStartHealth() {
		return startHealth;
	}

	public void setStartHealth(double startHealth) {
		this.startHealth = startHealth;
	}
	
	
	
}
