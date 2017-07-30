package ui.player.listener;

/**
 * Interface delineating the basic functionality of a listener that is updated
 * through polling on given cycles
 * 
 * @author Moses Wayne
 *
 */
public interface IListen {

	/**
	 * Method that resets the Queue of listened events to constantly poll for
	 * input
	 */
	public void pollQueue();
}
