/**
 * 
 */
package ui.player.ratings;


import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ui.Preferences;
import ui.general.CustomColors;
import ui.general.UIHelper;

/**
 * @author harirajan
 *
 */
public class RatingEntry extends VBox {
	
	private static final Insets DEFAULT_PADDING_INSETS = new Insets(10,10,10,10);
	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_BUTTON_HEIGHT = 50;
	private static final int DEFAULT_SPACING = 5;
	private static final Background DEFAULT_NODE_BACKGROUND = 
							new Background(new BackgroundFill(CustomColors.INDIGO, new CornerRadii(3.5), null));
	
	private TextArea myReviewText;
	private TextField myUserNameText;
	private Button mySubmitButton;
	private RatingStars myRatingStars;
	private ResourceBundle resource;
	
	public RatingEntry(int totalstars, String lang) {
		resource = ResourceBundle.getBundle(lang);
		UIHelper.setDropShadow(this);
		setSpacing(DEFAULT_SPACING);
		setBackground(DEFAULT_NODE_BACKGROUND);
		setPadding(DEFAULT_PADDING_INSETS);
		myRatingStars = new RatingStars(0, true, totalstars, lang);
		myReviewText = new TextArea();
		myReviewText.setPrefHeight(DEFAULT_WIDTH / 5);
		HBox myUsernameEntry = new HBox();
		myUserNameText = new TextField();
		myUserNameText.prefWidth(DEFAULT_WIDTH / 10);
		Label usernameLabel = new Label(resource.getString("usernameprompt"));
		usernameLabel.setFont(Preferences.FONT_MEDIUM_BOLD);
		usernameLabel.setTextFill(Color.WHITE);
		myUsernameEntry.getChildren().addAll(usernameLabel, myUserNameText);
		mySubmitButton = new Button(resource.getString("submitbuttontext"));
		mySubmitButton.setPrefSize(DEFAULT_WIDTH - 25, DEFAULT_BUTTON_HEIGHT);
		mySubmitButton.setTextFill(Color.WHITE);
		mySubmitButton.setFont(Preferences.FONT_SMALL_BOLD);
		mySubmitButton.setBackground(new Background(new BackgroundFill(CustomColors.INDIGO, null, null)));
		UIHelper.addClickAnimation(mySubmitButton);
		getChildren().addAll(myRatingStars, myUsernameEntry, myReviewText, mySubmitButton);
	}
	
	public void addSubmitEventHandler(EventHandler<ActionEvent> e) {
		mySubmitButton.addEventHandler(ActionEvent.ACTION, e);
		mySubmitButton.addEventHandler(ActionEvent.ACTION, event -> clear());
	}
	
	private void clear() {
		myRatingStars.clear();
		myUserNameText.clear();
		myReviewText.clear();
	}
	
	public String getUser() {
		return myUserNameText.getText();
	}
	
	public String getReview() {
		return myReviewText.getText();
	}
	
	public int getRating() {
		return myRatingStars.getRating();
	}
	
}
