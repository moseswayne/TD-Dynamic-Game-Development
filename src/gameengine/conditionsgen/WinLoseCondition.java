package gameengine.conditionsgen;

import java.util.Optional;

import gamestatus.ReadableGameStatus;

/**
 * Implementing abstract class of the Condition interface that guides the
 * options for the condition toward winning or losing.
 * 
 * @author Moses Wayne
 *
 */
public abstract class WinLoseCondition implements Condition {

	/**
	 * Method that determines a win or loss using the protected abstract helper
	 * methods that delineate what conditions constitute wins or losses. Return
	 * of the null value in the Boolean option indicates an unresolved win/loss
	 * condition (the game/level is still going).
	 * 
	 * @see Condition#conditionSatisfied(ReadableGameStatus)
	 */
	@Override
	public Optional<Boolean> conditionSatisfied(ReadableGameStatus status) {
		update();
		return (winCondition(status) || loseCondition(status) ? Optional.of(winCondition(status)) : Optional.empty());
	}

	/**
	 * Abstract helper method used to update the conditions being checked.
	 */
	protected abstract void update();

	/**
	 * Abstract helper method executed to determine the status of the game with
	 * regard to a win.
	 * 
	 * @param status
	 *            ReadableGameStatus object indicating the state of the
	 *            game/level
	 * @return boolean indicating if the user won
	 */
	protected abstract boolean winCondition(ReadableGameStatus status);

	/**
	 * Abstract helper method executed to determine the status of the game with
	 * regard to a loss.
	 * 
	 * @param status
	 *            ReadableGameStatus object indicating the state of the
	 *            game/level
	 * @return boolean indication of if the user lost
	 */
	protected abstract boolean loseCondition(ReadableGameStatus status);

}
