package ui.player.inGame;

import java.util.Optional;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Creates a simple sliding pane that includes a back button inside of a VBox to slide the pane back to it's original location
 * @author anngelyque
 */
public class SlidingPane extends AnchorPane{
	
	private VBox vbox;
	private OptionButton back;
	public static final String backIcon = "back_icon.png";
	
	public void setSlideTo(double d) {
		back.getButton().setOnAction(e -> slidePane(this, d));
	}
	
	public VBox getVBox() {
		return vbox;
	}
	
	public SlidingPane() {
		this(Optional.ofNullable(null), 0);
	}
	
	public SlidingPane(Optional<String> backImage, double slideTo) {
		this(backImage, slideTo, 30);
	}
	
	public SlidingPane(Optional<String> backImage, double slideTo, double spacing) {
		vbox = new VBox(spacing);
		addBackButton(backImage, slideTo);
	}
	
	private void addBackButton(Optional<String> backImage, double slideTo) {
		back = new OptionButton(0, "", backImage.orElse(backIcon), e -> slidePane(this, slideTo));
		vbox.getChildren().add(back.getButton());
	}
	
	public void slidePane(Pane pane, double xValue) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(0.2));
		t.setNode(pane);
		t.setToX(xValue);
		t.play();
	}
}
