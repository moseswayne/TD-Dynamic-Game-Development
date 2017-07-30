package gameengine.conditions;

import gameengine.conditionsgen.WinLoseCondition;
import gamestatus.ReadableGameStatus;

/**
 * Class extending the WinLoseCondition abstract class that holds the win
 * condition of having killed all enemies in a game/level and a lose condition
 * of having lost all lives.
 * 
 * @author Moses Wayne
 *
 */
public class KillAllCondition extends WinLoseCondition {

	/**
	 * Empty method for this class due to the nature of the win condition
	 */
	@Override
	protected void update() {

	}

	/**
	 * Win condition of if all enemies have been killed
	 */
	@Override
	protected boolean winCondition(ReadableGameStatus status) {
		return status.getEnemiesLeft() < 1;
	}

	/**
	 * Lose condition of if the user has lost all lives
	 */
	@Override
	protected boolean loseCondition(ReadableGameStatus status) {
		return status.getLives() < 1;
	}

}
