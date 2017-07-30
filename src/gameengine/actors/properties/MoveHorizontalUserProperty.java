package gameengine.actors.properties;

import gamedata.composition.MoveHorizontalUserData;
import gameengine.grid.interfaces.ActorGrid.ReadAndMoveGrid;

/**
 * Class extending the abstract class MoveAxisUserProperty that allows an actor
 * to move horizontally based on user input
 * 
 * @author Moses Wayne
 *
 * @param <G> generic grid extending the ReadAndMoveGrid interface
 */
public class MoveHorizontalUserProperty<G extends ReadAndMoveGrid> extends MoveAxisUserProperty<G> {

	public MoveHorizontalUserProperty(MoveHorizontalUserData myData) {
		super(myData.getMySensitivity());
	}

	@Override
	protected void move(G grid, Integer actorID) {
		grid.move(actorID, grid.getLocationOf(actorID).getX() + getKeyMoveX(grid, actorID),
				grid.getLocationOf(actorID).getY());
	}
}
