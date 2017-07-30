package gamedata.compositiongen;

import gameengine.actors.status.StatusDuration;
import types.BasicActorType;

/**
 * Defines the "status" of a given actor
 * 
 * @author maddiebriere
 *
 */

public abstract class StatusData extends ActData{
	
	BasicActorType myTarget;
	StatusDuration myLife;

	public StatusData(){
		this(new BasicActorType("Troop"), new StatusDuration(0));
	}
	
	public StatusData(BasicActorType type, StatusDuration statusDuration) {
		myTarget = type;
		myLife = statusDuration;
	}
	
	public StatusDuration getMyLife() {
		return myLife;
	}

	public void setMyLife(StatusDuration myLife) {
		this.myLife = myLife;
	}

	public BasicActorType getMyTarget() {
		return myTarget;
	}

	public void setMyTarget(BasicActorType myTarget) {
		this.myTarget = myTarget;
	}
	
}
