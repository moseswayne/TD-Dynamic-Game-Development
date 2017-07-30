package gamedata.compositiongen;

import gamedata.ActorData;

/**
 * A type of Data defining types of actions taken by the actor.
 * 
 * @author maddiebriere
 *
 */
public abstract class ActData extends Data{
	
	@Override
	public void addData(ActorData actor){
		actor.addData(this);
	}
}
