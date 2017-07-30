package gamedata.composition;

import java.util.List;

import types.BasicActorType;

/**
 * Actor is able to be damaged by other actors, and adds in
 * an event every time the actor dies.
 * 
 * @author maddiebriere
 *
 */

public class ActorDamageableWithExitData extends ActorDamageableData {

	public ActorDamageableWithExitData() {
		super();
	}

	public ActorDamageableWithExitData(Double myHitRadius, BasicActorType... myEnemyTypes) {
		super(myHitRadius, myEnemyTypes);
	}

	public ActorDamageableWithExitData(Double myHitRadius, List<BasicActorType> myEnemyTypes) {
		super(myHitRadius, myEnemyTypes);
	}
	
}
