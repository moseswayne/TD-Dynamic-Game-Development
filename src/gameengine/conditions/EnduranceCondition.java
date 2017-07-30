package gameengine.conditions;

import gameengine.conditionsgen.WinLoseCondition;
import gamestatus.ReadableGameStatus;

/**
 * Class extending the WinLoseCondition abstract class that discerns a win by
 * the number of frames/time of duration for which a level has run and a lose
 * condition of having lost all lives
 * 
 * @author Moses Wayne
 *
 */
public class EnduranceCondition extends WinLoseCondition {

	private Integer myFinalFrame;
	private Integer myCurrentFrame;

	public EnduranceCondition() {
		this(400);
	}

	public EnduranceCondition(Integer duration) {
		myFinalFrame = duration;
		myCurrentFrame = 0;
	}

	/**
	 * Updates the current frame count of the game
	 */
	@Override
	protected void update() {
		myCurrentFrame++;
	}

	/**
	 * Win condition of if a certain number of frames has been passed
	 */
	@Override
	protected boolean winCondition(ReadableGameStatus status) {
		return myCurrentFrame > myFinalFrame;
	}

	/**
	 * Lose condition of if the player has lost all lives
	 */
	@Override
	protected boolean loseCondition(ReadableGameStatus status) {
		return status.getLives() < 1;
	}

}
