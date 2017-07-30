package ui.player.login;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MiniGame{
	String name;
	String imagePath;
	EventHandler<MouseEvent> clicked;

	public String getName() {
		return name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public EventHandler<MouseEvent> getClicked() {
		return clicked;
	}

	public MiniGame(String name, String imagePath, EventHandler<MouseEvent> clicked) {
		this.name = name;
		this.imagePath = imagePath;
		this.clicked = clicked;
	}
}
