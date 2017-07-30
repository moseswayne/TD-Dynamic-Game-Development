package ui.authoring;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import gamedata.ActorData;
import gamedata.BasicData;
import gamedata.EnemyInWaveData;
import gamedata.GameData;
import gamedata.LineageData;
import gamedata.composition.LimitedHealthData;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import types.BasicActorType;
import ui.Preferences;
import ui.authoring.actor.ActorEditorView;
import ui.authoring.actor.CreateBasicTypeView;
import ui.authoring.delegates.ActorEditorDelegate;
import ui.authoring.delegates.CreateActorDelegate;
import ui.authoring.delegates.PopViewDelegate;
import ui.general.CustomColors;
import ui.general.UIHelper;

public class LeftPaneView extends StackPane implements CreateActorDelegate{
	
	private static final double ICON_WIDTH = 24;
	private static final double BUTTON_HEIGHT = 56;
	private final Color[] COLOR_ROTATION = {
			CustomColors.BLUE_800,
			CustomColors.INDIGO,
			CustomColors.GREEN,
			CustomColors.AMBER
	};
	
	private PopViewDelegate myPopDelegate;
	private ActorEditorDelegate myEditorDelegate;
	private VBox myVBox; //contains the buttons
	private Map<BasicActorType, ActorEditorView> actorTypeToView = new HashMap<>();
	private GameData myGameData;

	/**
	 * Constructs a panel with a list of button that
	 * lead to ActorEditorView
	 * Provides the ability to add new actor types.
	 * 
	 * @param delegate required so that this class can launch the ActorEditorView's
	 */
	public LeftPaneView(ActorEditorDelegate delegate, GameData gameData){
		super();
		myEditorDelegate = delegate;
		myPopDelegate = delegate;
		myGameData = gameData;
		setupViews();
	}
	
	private void setupViews() {
		setupVBox();
		setupTitle();
		setupAddButton();
		loadActors();
	}
	
	private void setupTitle() {
		Label title = new Label("Actor Editor");//TODO
		title.setTextFill(CustomColors.BLUE_50);
		title.setAlignment(Pos.CENTER);
		title.setFont(Preferences.FONT_MEDIUM);
		StackPane.setAlignment(title, Pos.TOP_CENTER);
		StackPane.setMargin(title, new Insets(12.0));
		this.getChildren().add(title);
	}

	private void setupVBox() {
		myVBox = new VBox(16);
		myVBox.setAlignment(Pos.CENTER);
		StackPane.setMargin(myVBox, new Insets(64,12,12,12));
		this.getChildren().add(myVBox);		
	}

	private void setupAddButton() {
		ImageView add = new ImageView(new Image("add_icon_w.png"));
		add.setFitWidth(ICON_WIDTH);
		add.setPreserveRatio(true);
		StackPane button = UIHelper.buttonStack(e -> addNewActor(), Optional.ofNullable(null), Optional.of(add), Pos.CENTER, true);
		button.setPrefHeight(BUTTON_HEIGHT);
		UIHelper.setBackgroundColor(button, Color.TRANSPARENT);
		myVBox.getChildren().add(button);
	}
	
	/**
	 * asks for an image icon and name for actor type
	 * creates an ActorEditorView for the new type of actor
	 */
	private void addNewActor() {
		Pane pane = new CreateBasicTypeView(this);
		pane.setPrefHeight(200);
		pane.setPrefWidth(200);
		
		myPopDelegate.openViewWithSize(pane, PopupSize.SMALL);
		
	}
	
	private void loadActors(){
		boolean troopFound = false;
		if(myGameData.getTypes().stream().filter(type -> type.getType().equals("Troop")).collect(Collectors.toList()).isEmpty())
			myGameData.getTypes().add(new BasicActorType("Troop", "enemy_icon.png", false));
		for(BasicActorType type: myGameData.getTypes()){
			//Map<String, LineageData> mapping = myGameData.getAllLinOfType(type)
			if(type.equals(new BasicActorType("Troop"))){
				troopFound = true;
			}
;			addActorView(type);
		}
		if(!troopFound){
			
		}
	}
	
	private void printCurrent(){
		System.out.print("****GameData review******\n");
		if(myGameData.getLevels().size()!=0 && myGameData.getLevel(1).getNumWaves()!=0)
			for(EnemyInWaveData enemy: myGameData.getLevel(1).getMyWaves().get(0).getWaveEnemies()){
				System.out.println("Level 1 ENEMY: " + enemy.getMyActor().getName());
			}
		for(ActorData actor: myGameData.getOptions().values()){
			System.out.print("ACTOR: "+actor.getName() +"\t");
			if(actor.getHealth() instanceof LimitedHealthData){
				System.out.println("Health: "+ ((LimitedHealthData)(actor.getHealth())).getStartHealth());
			}
		}
	}
	
	private void addActorView(BasicActorType actorType){
		ActorEditorView view = new ActorEditorView(myEditorDelegate, actorType, myGameData);
		
		//view.setupActors(gameData);
		
		UIHelper.setBackgroundColor(view, COLOR_ROTATION[this.actorTypeToView.size()%COLOR_ROTATION.length]);
		this.actorTypeToView.put(actorType, view);
		StackPane button = UIHelper.buttonStack(e -> launchEditor(view), 
				Optional.of(labelForStackButton(actorType + " Editor")), //TODO resources
				Optional.of(imageForStackButton(actorType.getImagePath())), 
				Pos.CENTER_RIGHT, true);
		button.setPrefHeight(BUTTON_HEIGHT);
		myVBox.getChildren().add(myVBox.getChildren().size() - 1, button);
	}

	private void addActorToGame(BasicActorType actorType){
		ActorEditorView view = new ActorEditorView(myEditorDelegate, actorType, myGameData);
		myGameData.getTypes().add(actorType);
	
		UIHelper.setBackgroundColor(view, COLOR_ROTATION[this.actorTypeToView.size()%COLOR_ROTATION.length]);
		this.actorTypeToView.put(actorType, view);
		StackPane button = UIHelper.buttonStack(e -> launchEditor(view), 
				Optional.of(labelForStackButton(actorType + " Editor")), //TODO resources
				Optional.of(imageForStackButton(actorType.getImagePath())), 
				Pos.CENTER_RIGHT, true);
		button.setPrefHeight(BUTTON_HEIGHT);
		myVBox.getChildren().add(myVBox.getChildren().size() - 1, button);
	}
	
	private void launchEditor(ActorEditorView view) {
		view.setGameData(myGameData);
		view.setActorTypeOptions(this.actorTypeToView.keySet());
		myPopDelegate.openView(view);
		printCurrent();
	}

	private Label labelForStackButton(String title){
		Label lbl = new Label(title);
		lbl.setTextFill(CustomColors.GREEN_100);
		lbl.setFont(Preferences.FONT_SMALL_BOLD);
		return lbl;
	}
	private ImageView imageForStackButton(String imagePath){
		Image image = null;
		try{
			image = new Image(imagePath);
		} catch(Exception e){
			BufferedImage im = null;
			try {
				im = ImageIO.read(new File(imagePath));
			} catch (IOException ee) {
				e.printStackTrace();
			}
			image = SwingFXUtils.toFXImage(im, null);
		}
		ImageView iv = new ImageView(image);
		iv.setFitWidth(ICON_WIDTH);
		iv.setPreserveRatio(true);
		return iv;
	}
	
	@Override
	public void closeSelfAndReturn(Pane pane, String actorName, String imagePath) {
		this.addActorToGame(new BasicActorType(actorName, imagePath));
		closeSelf(pane);
	}
	
	@Override
	public void closeSelf(Pane pane){
		myPopDelegate.closeView(pane);
	}
	
}
