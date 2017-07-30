package ui.authoring;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;

import gamedata.ActorData;
import gamedata.BasicData;
import gamedata.DisplayData;
import gamedata.GameData;
import gamedata.LineageData;
import gamedata.PreferencesData;
import gamedata.composition.LimitedHealthData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import types.BasicActorType;
import ui.Preferences;
import ui.authoring.actor.ActorInfoView;
import ui.authoring.delegates.DisplayDelegate;
import ui.authoring.delegates.PopViewDelegate;
import ui.authoring.display.DisplayMenu;
import ui.general.CustomColors;
import ui.general.ImageButton;
import ui.general.StackButton;
import ui.general.ToggleSwitch;
import ui.general.UIHelper;
import util.Location;

public class DisplayView extends BorderPane {

	private static final String NUM_LIVES_LABEL = "Number of Lives";
	private static final String ENEMY_LOOP_LABEL = "Tower Menu Position";
	private static final String TOWERS_ATTACKABLE_LABEL = "Attackable Towers";
	private static final String WANT_MONEY_LABEL = "Money";
	private static final String EXP_BY_LEVEL_LABEL = "Level Experience";
	private static final String PAUSE_BETWEEN_WAVES_LABEL = "Buffer Waves";
	private static final String CLEAN_LEVEL_LABEL = "Clean Level";
	private double myWidth;
	private double myHeight;
	private PopViewDelegate popViewDelegate;
	private DisplayDelegate displayDelegate;
	private DisplayData myData;
	private GameData myGameData;
	private VBox myVBox;
	private DisplayMenu myMenu;
	private String menuLocs[]={"Top","Left","Right","Bottom"};
	private Collection<String> mySwitchTitles;
	
	private Map<String, ComboBox<String>> myPreferences;

	public DisplayView(PopViewDelegate delegate,DisplayDelegate displayDelegate,GameData gameData) {
		this(delegate,displayDelegate, gameData.getDisplayData(),gameData);
	}
	
	public DisplayView(PopViewDelegate delegate,DisplayDelegate displayDelegate, DisplayData data, GameData gameData){
		super();
		popViewDelegate = delegate;
		this.displayDelegate=displayDelegate;
		myData = data;
		myGameData=gameData;
		myPreferences = new HashMap<>();
		mySwitchTitles = new ArrayList<>(Arrays.asList(ENEMY_LOOP_LABEL)); 
		setupViews();
	}

	
	

	private Label getPlainLabel(String s){
		Label label = new Label(s);
		label.setFont(Preferences.FONT_SMALL);
		label.setTextFill(Color.WHITE);
		return label;
	}


	

	/**
	 * 
	 */
//	
	private void getPositionComboBox(String title){
		ComboBox<String> posChoice=new ComboBox<String>();
		for(String s :menuLocs){
		posChoice.getItems().add(s);
		}
		posChoice.valueProperty().addListener((x, y, newValue) -> {
			myData.setLocation(newValue);
			TexttoPosFactory.updateMenuPosition(this, newValue, myMenu);
		});
		myVBox.getChildren().add(makeField(title, posChoice));
		myPreferences.put(title, posChoice);
	}
	public void updateDisplayMenu(){
	myMenu.updateDisplayList();
	}
	private void getWidthPrompt(){
		TextField field = new TextField();
		field.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                field.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	            try {
	            	int newWidth=Integer.parseInt(newValue);
	            	myData.setWidth(newWidth);
	            	myMenu.setWidth(newWidth);
	            } catch (Exception e) {}
	        }
	    });
		field.setMaxWidth(80);
		myVBox.getChildren().add(makeField("Width", field));
	}
	private void getHeightPrompt(){
		TextField field = new TextField();
		field.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                field.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	            try {
	            	int newHeight=Integer.parseInt(newValue);
	            	myData.setHeight(newHeight);
	            	myMenu.setHeight(newHeight);
	            } catch (Exception e) {}
	        }
	    });
		field.setMaxWidth(80);
		myVBox.getChildren().add(makeField("Height", field));
	}


	
	

	private StackButton makeField(String title, Node entry) {
		StackButton button = new StackButton();
		button.setShadow();
		Label label = new Label(title);
		label.setFont(Preferences.FONT_SMALL_BOLD);
		label.setTextFill(Color.WHITE);
		label.setPrefWidth(136);
		label.setAlignment(Pos.CENTER_LEFT);
		button.setIconNode(label);
		button.setIconInsets(new Insets(8, 8, 8, 8));
		button.setCenterNode(entry);
		button.setCenterInsets(new Insets(8, 8, 8, 200));
		button.setHeight(56);
		return button;
	}


	
	public DisplayData getData() {
		
		return myData;
	}
	private void setupBackButton() {
		ImageButton b = new ImageButton("back_icon.png", new Location(30., 30.));
		AnchorPane.setTopAnchor(b, 4.0);
		AnchorPane.setLeftAnchor(b, 4.0);
		b.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> popViewDelegate.closeView(this));
		this.getChildren().add(b);
	}
	


	
	
	private void setupViews() {
		ScrollPane leftSide = new ScrollPane();
		ScrollPane rightSide = new ScrollPane();
		setupSides(leftSide, rightSide);
		setupVBox(leftSide);
		setupBackButton();
		
	}
	
	
	
	

	private void setupSides(ScrollPane leftSide, ScrollPane rightSide) {
		double inset = 12.0;
		//AnchorPane.setBottomAnchor(leftSide, inset);
		
		//AnchorPane.setTopAnchor(leftSide, inset);
		
		//AnchorPane.setRightAnchor(leftSide, 350.0);
		

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

		this.setCenter(leftSide);
		 myMenu=new DisplayMenu(myData,displayDelegate,myGameData);
		
		this.setLeft(myMenu.getNode());

	}
	
	private void setupVBox(ScrollPane pane) {
		myVBox = new VBox();
		myVBox.setAlignment(Pos.CENTER);
		myVBox.prefWidthProperty().bind(pane.widthProperty().add(-2));
		pane.setContent(myVBox);
	getWidthPrompt();
	getHeightPrompt();
	getPositionComboBox("Tower Menu Position");

	}




	
	

	
	

}