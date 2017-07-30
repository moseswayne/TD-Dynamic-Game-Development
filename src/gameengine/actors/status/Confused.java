package gameengine.actors.status;

import gamedata.compositiongen.StatusData;
import gameengine.grid.interfaces.ActorGrid.ReadableGrid;

/**
 * 
 * 
 * @author Moses Wayne
 * @author sarahzhou
 */
public class Confused<G extends ReadableGrid> extends IStatus<G> {

	public Confused(StatusData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action(G grid, Integer actorID) {
		// TODO Auto-generated method stub
		
	}


}
