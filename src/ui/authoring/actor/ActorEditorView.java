package ui.authoring.actor;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import XML.xmlmanager.classes.ConcreteFileHelper;

import java.util.Optional;
import java.util.Set;

import gamedata.ActorData;
import gamedata.BasicData;
import gamedata.GameData;
import gamedata.LineageData;
import gamedata.composition.LimitedHealthData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import types.BasicActorType;
import ui.Preferences;
import ui.authoring.delegates.ActorEditorDelegate;
import ui.authoring.delegates.ActorInfoDelegate;
import ui.general.CustomColors;
import ui.general.ImageButton;
import ui.general.UIHelper;
import util.Location;

/**
 * Provides the user the ability to add new types of towers and customize their
 * properties.
 * 
 * Also loads the actors of the given BasicActorType
 * 
 * @author TNK
 * @author maddiebriere
 *
 */
public class ActorEditorView extends AnchorPane implements ActorInfoDelegate {
	private static final double BUTTON_HEIGHT = 72;
	
	
	private GameData myGameData;
	private ActorEditorDelegate myDelegate;
	private VBox myLineageList;
	private ActorInfoView myActorInfoView;
	private BasicActorType myActorType;

	public ActorEditorView(ActorEditorDelegate delegate, BasicActorType type, GameData gameData) {
		super();
		myDelegate = delegate;
		myActorType = type;
		myGameData = gameData;
		myGameData.getAllOfType(type);
		UIHelper.setBackgroundColor(this, CustomColors.BLUE_800);
		setupViews();
		setupActors();
	}

	private void setupBackButton() {
		ImageButton b = new ImageButton("back_icon.png", new Location(30., 30.));
		AnchorPane.setTopAnchor(b, 4.0);
		AnchorPane.setLeftAnchor(b, 4.0);
		b.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> myDelegate.closeView(this));
		this.getChildren().add(b);
	}
	
	private void setupPlaceable(){
		Label lbl = new Label("Placeable?");
		lbl.setTextFill(CustomColors.GREEN_100);
		lbl.setFont(Preferences.FONT_SMALL_BOLD);
		ImageButton b;
		if(myActorType.isPlaceable()){
			b = new ImageButton("place_icon.png", new Location(32., 32.));
		} else{
			b = new ImageButton("no_place_icon.png", new Location(32., 32.));
		}
		AnchorPane.setTopAnchor(b, 4.0);
		AnchorPane.setRightAnchor(b, 4.0);
		AnchorPane.setTopAnchor(lbl, 16.0);
		AnchorPane.setRightAnchor(lbl, 50.0);
		b.addEventHandler(MouseEvent.MOUSE_CLICKED, p -> togglePlaceable(b));
		this.getChildren().add(b);
		this.getChildren().add(lbl);
		UIHelper.setBackgroundColor(b, Color.TRANSPARENT);
	}

	private void togglePlaceable(ImageButton b){
		Image place = new Image("place_icon.png");
		Image noplace = new Image("no_place_icon.png");
		
		Image selected = place;
		
		if(myActorType.isPlaceable()){
			selected = noplace;
		}
		
		ImageView im = new ImageView(selected);
		im.setFitWidth(32);
		im.setFitHeight(32);
		b.setGraphic(im);
		myActorType.togglePlaceable();
	}
	
	private void setupViews() {
		ScrollPane leftSide = new ScrollPane();
		ScrollPane rightSide = new ScrollPane();
		setupSides(leftSide, rightSide);
		setupVBox(leftSide);
		setupAddActorButton();
		setupInfoView(rightSide);
		setupBackButton();
		setupPlaceable();
	}
	
	private void setupInfoView(ScrollPane scroll){
		myActorInfoView = new ActorInfoView(this, myGameData);
		myActorInfoView.prefWidthProperty().bind(scroll.widthProperty());
		myActorInfoView.minHeightProperty().bind(scroll.heightProperty());
		scroll.setContent(myActorInfoView);
	}
	
	private void setupAddActorButton() {
		Label label = new Label("Add New");
		label.setFont(Preferences.FONT_MEDIUM_BOLD);
		label.setTextFill(CustomColors.BLUE_50);
		ImageView imageView = new ImageView(new Image("add_icon_w.png"));
		imageView.setFitHeight(40);
		imageView.setPreserveRatio(true);
		StackPane view = UIHelper.buttonStack(e -> addNewActor(), 
				Optional.of(label), Optional.of(imageView), 
				Pos.CENTER_LEFT, true);
		view.setPrefHeight(BUTTON_HEIGHT);
		UIHelper.setBackgroundColor(view, CustomColors.BLUE_200);
		VBox.setMargin(view, new Insets(8));
		this.myLineageList.getChildren().add( view);

	}

	private void setupSides(ScrollPane leftSide, ScrollPane rightSide) {
		double inset = 12.0;
		AnchorPane.setBottomAnchor(rightSide, inset);
		AnchorPane.setBottomAnchor(leftSide, inset);
		AnchorPane.setTopAnchor(rightSide, 48.0);
		AnchorPane.setTopAnchor(leftSide, 48.0);
		AnchorPane.setRightAnchor(rightSide, inset);
		AnchorPane.setLeftAnchor(leftSide, inset);

		rightSide.setStyle("-fx-background-color: #" + UIHelper.colorToHex(CustomColors.BLUE_50) + ";");
		rightSide.setStyle("-fx-background: #" + UIHelper.colorToHex(CustomColors.BLUE_50) + ";");
		leftSide.setStyle("-fx-background-color: #" + UIHelper.colorToHex(CustomColors.BLUE_50) + ";");
		leftSide.setStyle("-fx-background: #" + UIHelper.colorToHex(CustomColors.BLUE_50) + ";");
	
		leftSide.setBorder(new Border(new BorderStroke[] {new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT) }));
		rightSide.setBorder(Border.EMPTY);
		

		leftSide.setHbarPolicy(ScrollBarPolicy.NEVER);
		rightSide.setHbarPolicy(ScrollBarPolicy.NEVER);
		leftSide.setVbarPolicy(ScrollBarPolicy.NEVER);
		rightSide.setVbarPolicy(ScrollBarPolicy.NEVER);

		UIHelper.setDropShadow(rightSide);
		UIHelper.setDropShadow(leftSide);
		rightSide.prefWidthProperty().bind(this.widthProperty().divide(3.0/2).subtract(inset * 3 / 2));
		leftSide.prefWidthProperty().bind(this.widthProperty().divide(3.0).subtract(inset * 3 / 2));

		this.getChildren().addAll(leftSide, rightSide);

	}

	private void setupVBox(ScrollPane pane) {
		myLineageList = new VBox();
		myLineageList.setAlignment(Pos.CENTER);
		myLineageList.prefWidthProperty().bind(pane.widthProperty().add(-2));
		pane.setContent(myLineageList);
	}

	private void setupActors() {
		for (LineageData entry : myGameData.getAllLinOfType(myActorType).values()) {
			addActorToView(entry);
		}
	}
	
	private void addActorData(LineageData data){
		myGameData.add(data);
		addActorToView(data);
	}

	/**
	 * This method adds a StackButton to the Vbox with the actor image
	 * and name. It also creates an ActorData and stores it in the 
	 * myTowers map, binding the ActorData to the StackButton
	 * 
	 * @param imgPath the String path of the image
	 * @param name the name of the actor, can be changed later.
	 */
	private void addActorToView(LineageData data){
		AnchorPane anchor = new AnchorPane();

		Image img = new Image(data.getProgenitor().getImagePath());
		ImageView imageView = new ImageView(img);
		imageView.setFitWidth(40);
		imageView.setPreserveRatio(true);
		
		TextField actorField = addField(data.getProgenitor().getName());
			
		StackPane view = UIHelper.buttonStack(
				e -> myActorInfoView.setLineageData(data), 
				Optional.of(actorField), Optional.of(imageView), 
				Pos.CENTER_LEFT, true);
		view.setPrefHeight(BUTTON_HEIGHT);
		actorField.textProperty().addListener((o,oldText,newText) -> this.updateTowerName(data, newText));

		/*ImageView removeIcon = new ImageView(new Image("clear_icon.png"));
		removeIcon.setFitHeight(16);
		removeIcon.setFitWidth(16);
		StackPane remove = UIHelper.buttonStack(e -> {removeLineage(data, anchor);}, 
				Optional.ofNullable(null), 
				Optional.of(removeIcon), Pos.CENTER, true);
		UIHelper.setBackgroundColor(remove, Color.TRANSPARENT);
		AnchorPane.setTopAnchor(remove, -4.0);
		AnchorPane.setRightAnchor(remove, -4.0);*/
		
		UIHelper.setBackgroundColor(view, CustomColors.BLUE_200);
		//VBox.setMargin(view, new Insets(8));
		AnchorPane.setLeftAnchor(view, 8.0);
		AnchorPane.setRightAnchor(view, 8.0);
		AnchorPane.setTopAnchor(view, 8.0);
		AnchorPane.setBottomAnchor(view, 8.0);
		anchor.getChildren().addAll(view);
		myLineageList.getChildren().add(myLineageList.getChildren().size() - 1, anchor);	
	}
	
	private void removeLineage(LineageData data, AnchorPane anchor){
		//TODO: Debug and add delegate
		myGameData.completeWipeLineage(data);
		//myDelegate.closeView(anchor);
		
	}
	
	
	public TextField addField(String name){
		StackPane lblWrapper = new StackPane();
		TextField field = new TextField(name);
		field.setFont(Preferences.FONT_MEDIUM);
		field.setAlignment(Pos.CENTER);
		field.setBackground(UIHelper.backgroundForColor(CustomColors.BLUE_200));
		field.setStyle("-fx-text-fill-color: #FFFFFF");
		field.setStyle("-fx-background-color: #" +UIHelper.colorToHex(CustomColors.BLUE_200) + ";");
		StackPane.setMargin(field, new Insets(8,32,8,32));
		lblWrapper.getChildren().add(field);
		return field;
	}

	/**
	 * the action when the plus button is pressed on the bottom of the screen
	 * prompts user to select an image and adds a new tower with default values
	 */
	private void addNewActor() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
		if (selectedFile != null) {
			ConcreteFileHelper manager = new ConcreteFileHelper();
			try {
				manager.moveFile(selectedFile.getParent(), "images", selectedFile.getName());
				
			} catch (Exception e1) {}
			String imageName = selectedFile.getName();
			String name = imageName.substring(0, imageName.indexOf("."));
			LineageData lin = new LineageData(new ActorData(myActorType, 
					new BasicData(name,  selectedFile.toURI().toString()), new LimitedHealthData()));
			addActorData(lin);
		}
	}
	
	public void setGameData(GameData data){
		myGameData = data;
	}
	
	private void updateTowerName(LineageData data, String text){
		for(ActorData actor : data.getMap().values()){
			actor.getBasic().setName(text);
		}
	}
	
//	public Collection<LineageData> getActorData() {
//		return	myActors.values();
//	}

	public void setActorTypeOptions(Set<BasicActorType> keySet) {
		this.myActorInfoView.setActorTypeOptions(keySet);
		
	}

@Override
public void addActorToBase(ActorData data, Location mouseLoc) {
	myDelegate.addActorToBase(data, mouseLoc);
	myDelegate.closeView(this);
}
	
	

}
