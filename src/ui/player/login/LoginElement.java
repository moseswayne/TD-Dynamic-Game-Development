package ui.player.login;

import javafx.scene.Scene;

/**
 * For elements pertaining to the homescreen. Ensures that the scene from these
 * visual elements can be painted onto the main {@code Stage}
 * @author Vishnu Gottiparthy
 *
 */
public interface LoginElement {
	
	/**
	 * Gets the {@code Scene} corresponding to this element
	 * @return The {@code Scene} corresponding to this element
	 */
	public Scene getScene();
}
