package ui.player.listener;

import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Modular class that is takes in a scene and reports an ListenQueue to the end
 * client when polled.
 * 
 * @author Moses Wayne
 *
 */
public class SceneListen implements IListen {

	private ListenQueue myQueue;
	private Grid2D myMouse;

	public SceneListen(Scene listen) {
		myMouse = new Coordinates(0, 0);
		generateNewQueue();
		listen.addEventHandler(KeyEvent.KEY_PRESSED, handleKeys());
		listen.addEventHandler(MouseEvent.MOUSE_MOVED, pollMouse(listen));
	}

	/**
	 * Method to generate an event handler to handle the input of key strokes
	 * and add them to the present queue
	 * 
	 * @return EventHandler using KeyEvents as the input that adds key events to
	 *         a queue
	 */
	private EventHandler<KeyEvent> handleKeys() {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				myQueue.addEvent(event.getCode());
			}
		};
	}

	/**
	 * Method to generate an event handler to handle recording the movement of
	 * the mouse/cursor
	 * 
	 * @param scene
	 *            Scene object from which the size of the game screen can be
	 *            obtained and scaled
	 * @return Event Handler updating the location of the mouse within the
	 *         ListenQueue
	 */
	private EventHandler<MouseEvent> pollMouse(Scene scene) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				myMouse = new Coordinates(event.getSceneX() / scene.getWidth(), event.getSceneY() / scene.getHeight());
			}
		};
	}

	/**
	 * Method to reset the ListenQueue
	 */
	private void generateNewQueue() {
		myQueue = new ListenQueue(myMouse.getX(), myMouse.getY());
	}

	/**
	 * Method to return the ListenQueue
	 * 
	 * @return the current ListenQueue
	 */
	public ListenQueue getQueue() {
		return myQueue;
	}

	/**
	 * Public method to call the reset on the ListenQueue
	 */
	public void pollQueue() {
		generateNewQueue();
	}
}
