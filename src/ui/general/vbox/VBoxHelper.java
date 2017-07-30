package ui.general.vbox;

import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class VBoxHelper {
	public static Optional<Node> getLabelWithColor(Color c, String title){
		Label label = new Label(title);
		label.setTextFill(c);
		return Optional.of(label);
	}
	public static Optional<ImageView> getImageView(Image image, double size){
		ImageView view = new ImageView(image);
		view.setFitHeight(size);
		view.setFitWidth(size);
		return Optional.of(view);
	}
}
