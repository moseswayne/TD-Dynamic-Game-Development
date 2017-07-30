package ui.player.listener;

import java.util.HashSet;
import java.util.Set;

import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.scene.input.KeyCode;

/**
 * Data structure class wrapping mouse position and key stroke history into
 * 
 * @author Moses Wayne
 *
 */
public class ListenQueue {

	private Grid2D myPointer;
	private Set<KeyCode> myEvents;

	public ListenQueue(double xLoc, double yLoc) {
		myPointer = new Coordinates(xLoc, yLoc);
		myEvents = new HashSet<>();
	}

	/**
	 * Method to return a Grid2D representation of the location of the cursor,
	 * scaled to dimensions of 1 by 1
	 * 
	 * @return Grid2D representation of the location of the cursor
	 */
	public Grid2D getLocation() {
		return myPointer;
	}

	/**
	 * Method to add a KeyCode to the Set of KeyCodes held in the ListenQueue
	 * 
	 * @param code
	 *            KeyCode to be added
	 */
	public void addEvent(KeyCode code) {
		myEvents.add(code);
	}

	/**
	 * Method to query the ListenQueue for the presence of an input in a given
	 * cycle
	 * 
	 * @param key
	 *            KeyCode to be queried
	 * @return boolean indicating the KeyCode's presence
	 */
	public boolean queryKey(KeyCode key) {
		return myEvents.contains(key);
	}
}
