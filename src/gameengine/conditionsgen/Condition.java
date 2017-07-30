package gameengine.conditionsgen;

import java.util.Optional;

import gamestatus.ReadableGameStatus;

/**
 * Condition interface defining the conditions for an event to occur according
 * to the GameStatus. Returns an optional for a boolean, flagging whether an
 * event has occurred.
 * 
 * @author Moses Wayne
 * @author maddiebriere
 */
public interface Condition {

	/**
	 * Primary method for the Condition interface that takes in a
	 * ReadableGameStatus as a parameter to determine whether a given condition
	 * has been satisfied using an Optional of a boolean.
	 * 
	 * @param status
	 *            a ReadableGameStatus object that indicates the current state
	 *            of the game/level being run
	 * @return Optional of a Boolean that determines the state of whether a
	 *         condition has been satisfied. A null return is indicative of an
	 *         unresolved condition, whereas the return of a Boolean indicates
	 *         whether the condition was successfully or unsuccessfully
	 *         completed.
	 */
	public Optional<Boolean> conditionSatisfied(ReadableGameStatus status);
}
