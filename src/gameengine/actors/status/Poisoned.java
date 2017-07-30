package gameengine.actors.status;

import gamedata.compositioncon.PoisonedStatusData;
import gameengine.grid.interfaces.ActorGrid.ReadAndDamageGrid;
import util.Delay;

/**
 * 
 * 
 * @author sarahzhou
 */
public class Poisoned<G extends ReadAndDamageGrid> extends IStatus<G>{
	
	double myDamage;
	double myTimeBetweenDamage;
	Delay myDelay;

	public Poisoned(PoisonedStatusData data) {
		super(data);
		myDamage = data.getMyDamage();
		myDelay = new Delay(data.getMyTimeBetweenDamage());
	}

	@Override
	public void action(G grid, Integer actorID) {
		grid.getMyDamageable(actorID).accept(myDamage);
	}
	
	@Override
	public boolean isOn() {
		return (super.isOn()&&myDelay.delayAction());
	}

	
}