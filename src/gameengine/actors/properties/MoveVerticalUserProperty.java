package gameengine.actors.properties;

import gamedata.composition.MoveVerticalUserData;
import gameengine.grid.interfaces.ActorGrid.ReadAndMoveGrid;

/**
 * Class extending the abstract class MoveAxisUserProperty that allows an actor
 * to move veritcally based on user input
 * 
 * @author Moses Wayne
 *
 * @param <G> generic grid extending the ReadAndMoveGrid interface
 */
public class MoveVerticalUserProperty<G extends ReadAndMoveGrid> extends MoveAxisUserProperty<G> {
	
	public MoveVerticalUserProperty(MoveVerticalUserData myData) {
		super(myData.getMySensitivity());
	}

	@Override
	protected void move(G grid, Integer actorID) {
		grid.move(actorID, grid.getLocationOf(actorID).getX(), grid.getLocationOf(actorID).getY()+getKeyMoveY(grid, actorID));
	}

}
