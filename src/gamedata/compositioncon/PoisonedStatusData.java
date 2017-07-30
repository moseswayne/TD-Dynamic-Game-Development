package gamedata.compositioncon;

import gamedata.compositiongen.StatusData;
import gameengine.actors.status.StatusDuration;
import types.BasicActorType;

/**
 * @author sarahzhou
 *
 */
public class PoisonedStatusData extends StatusData{
	
	double myDamage;
	int myTimeBetweenDamage;
	
	public PoisonedStatusData(){
		super();
		myDamage=0;
		myTimeBetweenDamage=0;
	}

	public PoisonedStatusData(double damage, int timeBetweenDamage, BasicActorType type, StatusDuration statusDuration) {
		super(type, statusDuration);
		myDamage = damage;
		myTimeBetweenDamage = timeBetweenDamage;
	}
	
	public double getMyDamage() {
		return myDamage;
	}

	public void setMyDamage(double myDamage) {
		this.myDamage = myDamage;
	}
	
	public int getMyTimeBetweenDamage() {
		return myTimeBetweenDamage;
	}

	public void setMyTimeBetweenDamage(int myTimeBetweenDamage) {
		this.myTimeBetweenDamage = myTimeBetweenDamage;
	}
}
