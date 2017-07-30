package gameengine.handlers;

import java.util.function.Function;

import gameengine.grid.interfaces.controllergrid.ControllableGrid;
import types.BasicActorType;

public interface LevelHandler {
	ControllableGrid getMyGrid();
	void displayWinAlert();
	void displayLoseAlert();
	void levelUp();
	Function<BasicActorType,Integer> actorCounts();
	BasicActorType getBasicActorTypeEnemy();
}
