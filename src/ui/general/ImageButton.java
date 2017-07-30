package ui.general;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import util.Location;

/**
 * A button with an image
 * 
 * @author TNK
 *
 */
public class ImageButton extends Button{
	
	/**
	 * 
	 * @param imagePath the path to the image file.
	 * @param size A tuple describing the width and height of the image
	 */
	public ImageButton(String imagePath,Location size){
		this(imagePath, imagePath, size);
	}
	
	public ImageButton(String selected, String unselected, Location size){
		super();
		updateImages(new Image(selected),
				new Image(unselected), size);
	}
	
	public void updateImages(final Image selected, final Image unselected, Location size) {
		this.setBackground(Background.EMPTY);
		this.setBorder(Border.EMPTY);
		final ImageView iv = new ImageView(selected);
		iv.setPreserveRatio(true);
		iv.setFitWidth(size.getX());
		iv.setFitHeight(size.getY());
		this.getChildren().add(iv);
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			iv.setImage(unselected);
			iv.setScaleX(1.2);
			iv.setScaleY(1.2);
		});
		this.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			iv.setImage(selected);
			iv.setScaleX(1.);
			iv.setScaleY(1.);
		});
		super.setGraphic(iv);
	}

}