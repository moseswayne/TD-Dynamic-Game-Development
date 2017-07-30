package ui.authoring.delegates;

import gamedata.GameData;

/**
 * this interface defines how the MenuView class can interact with the AuthoringView
 * @author TNK
 *
 */
public interface MenuDelegate {
	
	public abstract void didPressBackButton();
	public abstract void didPressLoadButton();
	public abstract void didPressSaveButton();
	public abstract void didPressReturnMain();
	
}
