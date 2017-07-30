package gameengine.grid.interfaces.controllerinfo;

import gamestatus.WriteableGameStatus;
import ui.player.listener.ListenQueue;

public interface GridHandler {

	/**
	 * @return the WriteableGameStatus associated with the grid instance such that actors can edit the
	 * status of the game. For example, dying and adding xp to to WriteableGameStatus
	 */
	WriteableGameStatus getWriteableGameStatus();
	
	
	/**
	 * @return the event queue which contains information
	 * about the keys being pressed such that the actors can listen to it
	 * and act accordingly
	 */
	ListenQueue getEventQueue();
	
}
