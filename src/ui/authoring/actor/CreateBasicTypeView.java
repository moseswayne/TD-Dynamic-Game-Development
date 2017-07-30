package ui.authoring.actor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ui.Preferences;
import ui.authoring.delegates.CreateActorDelegate;
import ui.general.CustomColors;
import ui.general.UIHelper;
import util.generator.ImageInfo;
import util.generator.WebImageCollector;

/**
 * Purpose of this class is to provide the user a 
 * graphical way to pick an image and input a 
 * name for a new actor type.
 * 
 * It has a cancel and create button, the latter of
 * which is only selectable when the user has input
 * both things
 * @author TNK
 * @author maddiebriere
 *
 */
public class CreateBasicTypeView extends VBox {
	
	private final String NO_IMAGE = "no_image_icon.png";
	
	private TextField myTextField;
	private String myImagePath;
	private ImageView myImageView;
	private HBox myHBox;
	private CreateActorDelegate myDelegate;
	private Random randy;
	private List<String>hits;
	private List<Integer> hitIters;
	
	public CreateBasicTypeView(CreateActorDelegate delegate){
		super();
		myDelegate = delegate;
		randy = new Random();
		hits = new ArrayList<String>();
		hitIters = new ArrayList<Integer>();
		setupViews();
		
	}

	private void setupViews() {
		setupButtons();
		UIHelper.setBackgroundColor(this, CustomColors.GREEN_200);

		myImageView = new ImageView(new Image(NO_IMAGE));
		myImageView.setFitHeight(96);
		myImageView.setPreserveRatio(true);
		StackPane imageButton = UIHelper.buttonStack(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Image File");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
			File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
			if(selectedFile!= null){
				myImagePath = selectedFile.getName();
				System.out.println(myImagePath);
				myImageView.setImage(new Image(myImagePath));
			}
		}, Optional.ofNullable(null), Optional.of(myImageView), Pos.CENTER, true);
		UIHelper.setBackgroundColor(imageButton, CustomColors.GREEN_50);
		VBox.setMargin(imageButton, new Insets(12.0));
		imageButton.setMaxHeight(104);
		imageButton.setMaxWidth(104);
		
		StackPane add = buttonForName("Randomize Image", CustomColors.BLUE_50, e -> {
			String qry =  myTextField.textProperty().getValue();
			ImageInfo im = WebImageCollector.
					findAndSaveRandomIcon(randy, qry, hits, hitIters);
			myImagePath = im.getMyPath();
			System.out.println(myImagePath);
			Image image = SwingFXUtils.toFXImage(im.getMyImage(), null);
			myImageView.setImage(image);
		});
		VBox.setMargin(add, new Insets(8.0,8.0,8.0,8.0));
		this.getChildren().add(add);
		
		myTextField = new TextField();
		StackPane.setAlignment(myTextField, Pos.CENTER);
		VBox.setMargin(myTextField, new Insets(12,24,12,24));
		
		Label label = new Label("Please enter name for actor type"); //TODO
		label.setAlignment(Pos.CENTER);
		label.setFont(Preferences.FONT_SMALL);
		label.setTextFill(CustomColors.GREEN_50);
		VBox.setMargin(label, new Insets(16.0, 8,0,8));
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(imageButton,label, myTextField, myHBox);
	}

	private void setupButtons() {
		myHBox = new HBox();
		myHBox.setAlignment(Pos.CENTER);
		myHBox.setMaxHeight(64);
		VBox.setMargin(myHBox, new Insets(16,8,8,8));
		UIHelper.setBackgroundColor(myHBox, Color.TRANSPARENT);
		

		StackPane cancel =  buttonForName("Cancel", CustomColors.BLUE_50, e -> myDelegate.closeSelf(this));
		StackPane add = buttonForName("Confirm", CustomColors.BLUE_50, e -> {
			if(check())
				myDelegate.closeSelfAndReturn(this, this.myTextField.getText(), myImagePath);
			});
		myHBox.getChildren().addAll(cancel, add);
	}
	
	private StackPane buttonForName(String name, Color color, EventHandler<MouseEvent> event){
		Label label = new Label(name);
		label.setFont(Preferences.FONT_MEDIUM);
		label.setAlignment(Pos.CENTER);
		label.setTextFill(color);
		label.setPrefHeight(32);
		StackPane pane = UIHelper.buttonStack(event, 
				Optional.of(label), Optional.ofNullable(null), Pos.CENTER, true);
		pane.setPrefHeight(48);
		pane.setPrefWidth(96);
		HBox.setMargin(pane, new Insets(12.0));
		UIHelper.setBackgroundColor(pane, CustomColors.BLUE_800);
		return pane;
	}
	
	private boolean check(){
		return !myTextField.getText().equals("") && myImagePath != null && !myImagePath.equals("");
	}
	
	
}
