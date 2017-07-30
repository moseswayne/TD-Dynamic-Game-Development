package ui.player;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProfileCornerPicture extends ImageView{
	
	public ProfileCornerPicture(String image, EventHandler<MouseEvent> callCard) {
		this.setImage(new Image(image, 50, 50, false, true));
		this.setOnMouseClicked(callCard);
	}
	
}
