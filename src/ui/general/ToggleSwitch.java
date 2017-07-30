/**
 * 
 */
package ui.general;

/**
 * @author harirajan
 *
 */
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ui.Preferences;

public class ToggleSwitch {
	
	private static final String ON_COLOR_HEX = "64d668";
	private static final String OFF_COLOR_HEX = UIHelper.colorToHex(Color.LIGHTGREY);
	private static final int SIZE = 4;

	private final HBox myHBox;
	private final Label myLabel;
	private final Button myButtton;
	private final String myName;

	private SimpleBooleanProperty switchedOn;

	public ToggleSwitch(String name) {
		myName = name;
		myHBox = new HBox();
		myLabel = new Label();
		myButtton = new Button();
		switchedOn = new SimpleBooleanProperty(false);
		myLabel.setText("OFF");
		myHBox.getChildren().addAll(myLabel, myButtton);
		myButtton.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		myLabel.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		setStyle();
		bindProperties();
		switchedOn.addListener((a, b, c) -> {
			if (c) {
				myLabel.setText("ON");
				myHBox.setStyle(String.format("-fx-background-color: #%s;", ON_COLOR_HEX));
				myLabel.toFront();
			} else {
				myLabel.setText("OFF");
				myHBox.setStyle(String.format("-fx-background-color: #%s;", OFF_COLOR_HEX));
				myButtton.toFront();
			}
		});
	}

	private void setStyle() {
		myHBox.setPrefWidth(80);
		myLabel.setAlignment(Pos.CENTER);
		myHBox.setStyle(String.format("-fx-background-color: #%s; -fx-background-radius: %d;", 
									OFF_COLOR_HEX, SIZE));
		myHBox.setAlignment(Pos.CENTER_LEFT);
	}

	private void bindProperties() {
		myLabel.prefWidthProperty().bind(myHBox.widthProperty().divide(2));
		myLabel.prefHeightProperty().bind(myHBox.heightProperty());
		myButtton.prefWidthProperty().bind(myHBox.widthProperty().divide(2));
		myButtton.prefHeightProperty().bind(myHBox.heightProperty());
	}

	public boolean switchOnProperty() {
		return switchedOn.get();
	}
	
	public void setListener(Runnable runnable)  {
		switchedOn.addListener((a,b,c) -> {
			runnable.run();
		});
	}

	public String getName() {
		return myName;
	}

	public Node getNode() {
		return myHBox;
	}
}