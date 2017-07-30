package ui.authoring.map.layer;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ui.Preferences;
import ui.general.CustomColors;
import ui.general.UIHelper;

/**
 * This class is meant to provide a String input field for
 * the user to type in a name for the new layer that he
 * wants to make.
 * 
 * Internal implementation is a basically a Label and TextField centered
 * vertically in a VBox
 * 
 * @author TNK
 *
 */
public class LayerPopupView extends VBox{
	
	private TextField myTextField;
	private LayerPopupDelegate myDelegate;
	public LayerPopupView(LayerPopupDelegate delegate){
		super();
		myDelegate = delegate;
		setup();
	}
	private void setup() {
		UIHelper.setBackgroundColor(this, CustomColors.BLUE_200);
		setupInput();
		setupButtons();
	}
	private void setupButtons() {
		HBox box = new HBox();
		VBox.setMargin(box, new Insets(12.0));
		box.setAlignment(Pos.CENTER);
		box.setPrefHeight(80);
		StackPane cancel = UIHelper.buttonStack(
				e -> myDelegate.layerPopupDidPressCancel(), 
				Optional.of(labelForString("Cancel")), 
				Optional.ofNullable(null), Pos.CENTER, true);
		StackPane confirm = UIHelper.buttonStack(
				e -> getStringInput().ifPresent( 
						val -> myDelegate.layerPopupDidPressConfirm(val)), 
				Optional.of(labelForString("Confirm")), 
				Optional.ofNullable(null), Pos.CENTER, true);
		cancel.setPrefHeight(64);
		confirm.setPrefHeight(64);
		cancel.setPrefWidth(120);
		confirm.setPrefWidth(120);
		UIHelper.setBackgroundColor(confirm, CustomColors.BLUE_500);
		UIHelper.setBackgroundColor(cancel, CustomColors.BLUE_500);
		HBox.setMargin(confirm, new Insets(12.0));
		HBox.setMargin(cancel, new Insets(12.0));
		box.getChildren().addAll(cancel, confirm);
		this.getChildren().add(box);
	}
	
	private Optional<String> getStringInput() {
		Optional<String> op;
		if(myTextField.getText() != null &&
				myTextField.getText() != ""){ //also check that layer doesn't already exist
			op = Optional.ofNullable(myTextField.getText());
		}else{
			op=Optional.ofNullable(null);
		}
		return op;
	}
	private Label labelForString(String text){
		Label l = new Label(text);
		l.setTextFill(CustomColors.BLUE_50);
		l.setFont(Preferences.FONT_SMALL_BOLD);
		l.setAlignment(Pos.CENTER);
		return l;
	}
	
	private void setupInput() {
		Label label = labelForString("Please enter a name for the layer.");
		label.setAlignment(Pos.CENTER);
		
		myTextField = new TextField();
		myTextField.setAlignment(Pos.CENTER);
		myTextField.setFont(Preferences.FONT_MEDIUM_BOLD);
		VBox.setMargin(label, new Insets(12.0));
		VBox.setMargin(myTextField, new Insets(12.0));
		label.setPrefHeight(40);
		myTextField.setPrefHeight(40);
		this.getChildren().addAll(label,myTextField);
		
	}
	
	
}
