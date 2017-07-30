package ui.authoring.actor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import builders.objectgen.DataGenerator;
import gamedata.ActorData;
import gamedata.BasicData;
import gamedata.GameData;
import gamedata.LineageData;
import gamedata.compositiongen.Data;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import types.BasicActorType;
import ui.Preferences;
import ui.authoring.delegates.ActorInfoDelegate;
import ui.authoring.delegates.DataViewDelegate;
import ui.authoring.delegates.OptionPickerDelegate;
import ui.general.CustomColors;
import ui.general.UIHelper;
import util.Location;

/**
 * @author TNK
 * @author maddiebriere
 */
public class ActorInfoView extends AnchorPane implements DataViewDelegate, OptionPickerDelegate{
	
	private ActorInfoDelegate myDelegate;
	private GridPane myGridPane;
	private static final int GRID_X_DIM = 3;
	private HBox myUpgradePickerView;
	private LineageData myLineageData;
	private List<DataView> myDataViews;
	private List<StackPane> myActors;
	private ImageView myActorImageView;
	private DataSelectionView myOptionPickerView;
	private Set<BasicActorType> myActorTypeOptions;
	private ActorData myCurrentActorData;
	private GameData myGameData;
	

	
	public ActorInfoView(ActorInfoDelegate delegate, GameData gameData){
		super();
		myDelegate = delegate;
		myGameData = gameData;
		myDataViews = new ArrayList<DataView>();
		myActors = new ArrayList<StackPane>();
		setupViews();
	}
	
	private void setupViews(){
		setupLayout();
		this.setupAddNewClassButton();
		setupAddGenButton();
	}
	
	private void setupAddGenButton(){
		ImageView add = imageForStackButton("add_icon_w.png");
		StackPane addButton = UIHelper.buttonStack(e -> {addUpgrade();}, Optional.ofNullable(null), 
				Optional.of(add), Pos.CENTER, true);
		HBox.setMargin(addButton, new Insets( 38.0, 12.0, 38.0, 12.0));
		this.myUpgradePickerView.getChildren().add(addButton);
	}
	
	private void addUpgrade(){
		ActorData upgrade = new ActorData(myCurrentActorData.getType(), 
				new BasicData(myCurrentActorData.getName() + " " +
						(myLineageData.getMap().keySet().size()+1), myCurrentActorData.getImagePath()));
		setupImageView(new Image(myCurrentActorData.getImagePath()), upgrade);
		myLineageData.addGeneration(upgrade);
		selectActorData(upgrade);
	}
	
	private void printCurrent(){
		System.out.println("Lineage size: " + myLineageData.getMap().keySet().size());
		for(ActorData a: myLineageData.getMap().values()){
			System.out.println("Actor: " + a.getName());
		}
		System.out.println("My current actor: "+ myCurrentActorData.getName());
		System.out.println("Number of datas: " + myCurrentActorData.getMyData().size());
	}
	
	private ImageView imageForStackButton(String imagePath){
		Image img = new Image(imagePath);
		ImageView imageView = new ImageView(img);
		imageView.setFitWidth(40);
		imageView.setPreserveRatio(true);
		return imageView;
	}

	private void addLabel(String label, AnchorPane toAdd, double offset){
		Label fieldName = new Label(label);
		fieldName.setTextFill(CustomColors.BLUE_800);
		fieldName.setTextAlignment(TextAlignment.CENTER);
		AnchorPane.setLeftAnchor(fieldName, 4.0);
		AnchorPane.setTopAnchor(fieldName, 4.0 + offset);
		AnchorPane.setBottomAnchor(fieldName, 4.0);
		fieldName.setMaxWidth(80);
		toAdd.getChildren().add(fieldName);
	}
	
	public TextField addField(String value, double width){
		StackPane lblWrapper = new StackPane();
		TextField field = new TextField(value);
		field.setPrefWidth(width);
		field.setPrefHeight(20);
		field.setFont(Preferences.FONT_SMALL);
		field.setAlignment(Pos.CENTER);
		field.setBackground(UIHelper.backgroundForColor(CustomColors.BLUE_50));
		field.setStyle("-fx-text-fill-color: #FFFFFF");
		field.setStyle("-fx-background-color: #" +UIHelper.colorToHex(CustomColors.BLUE_50) + ";");
		lblWrapper.getChildren().add(field);
		return field;
	}
	
	private StackPane setupImageView(Image img, ActorData actor) {
		double width = 50;
		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		
		myActorImageView = new ImageView(img);
		ImageView local = myActorImageView;
		//myActorImageView.setFitHeight(myUpgradePickerView.getPrefHeight()*(1/8));
		myActorImageView.setFitHeight(64);
		myActorImageView.setFitWidth(64);
		myActorImageView.setPreserveRatio(true);
		StackPane button = UIHelper.buttonStack(e -> 
			{selectActorData(actor, local);}, Optional.ofNullable(null), 
				Optional.of(myActorImageView), Pos.CENTER, true);
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {highlightCurrentActor(button);});
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {doubleClick(actor, local, e);});
		UIHelper.addPressAndHoldHandler(button, Duration.seconds(0.3), e -> myDelegate.addActorToBase(actor, new Location(e.getSceneX(), e.getScreenY())));

		
		button.heightProperty().addListener(e -> {
			//TODO?
		});
		highlightCurrentActor(button);
		myActors.add(button);
		root.getChildren().add(button);
		
		AnchorPane content = new AnchorPane();
		VBox box = new VBox();
		box.setSpacing(10);
		box.setAlignment(Pos.CENTER);
		
		addLabel("Cost:", content, -20);
		TextField field = addField(""+actor.getCost(), width);
		field.textProperty().addListener((o,oldText,newText) -> {
			updateCost(actor, (String)newText);
		});
		
		addLabel("Layer:", content, 40);
		String layer = "";
		if(actor.getLayerName()!=null){
			layer = actor.getLayerName();
		}
		Picker<String> layers = 
				new Picker<String>
				(""+layer, new ArrayList<>(myGameData.getLayers().getMyLayers().keySet()), false);
		layers.setBackground(UIHelper.backgroundForColor(CustomColors.BLUE_50));
		layers.addToggle();
		layers.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {updateLayer(actor, layers.getTypeProperty().get());});
		layers.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
			layers.setMyTypes(new ArrayList<>(myGameData.getLayers().getMyLayers().keySet()));});
		
		
		AnchorPane.setRightAnchor(box, 4.0);
		AnchorPane.setTopAnchor(box, 4.0);
		AnchorPane.setBottomAnchor(box, 4.0);
		AnchorPane.setLeftAnchor(box, width);
		
		UIHelper.setBackgroundColor(content, CustomColors.BLUE_200);
		box.getChildren().addAll(field, layers);
		content.getChildren().add(box);
		
		VBox.setMargin(content, new Insets(8.0));
		root.getChildren().add(content);
		HBox.setMargin(root, new Insets(8));
		this.myUpgradePickerView.getChildren().add(root);
		return button;
	}
	
	private void doubleClick(ActorData actor, ImageView toEdit, MouseEvent mouseEvent){
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                System.out.println("Double clicked");
                updateImage(actor,toEdit);
            }
        }
	}
	
	private void updateImage(ActorData actor, ImageView view){
		String imagePath = getImageFile();
		view.setImage(new Image(imagePath));
		view.setFitHeight(64);
		view.setFitWidth(64);
		view.setPreserveRatio(true);
		actor.getBasic().setImagePath(imagePath);
	}
	
	private String getImageFile(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
		if(selectedFile!= null){
			return selectedFile.getName();
		}
		return "profile_icon.png";
	}
	
	private void updateLayer(ActorData actor, String newLayer){
		if(!newLayer.equals("")){
			actor.setLayer(myGameData.getLayers().getMyLayers().get(newLayer));
			actor.setLayerName(newLayer);
		}
	}
	
	private void updateCost(ActorData actor, String newText){
		double newCost = 0.0;
		try{
			newCost = Double.parseDouble(newText);
			actor.setCost(newCost);
		}catch(Exception e){
			//TODO: Error handling
		}
		System.out.println(newCost);
	}
	
	private void setupLayout() {
		myGridPane = new GridPane();
		myUpgradePickerView = new HBox(8.0);
		double inset = 10.0;
		double prefHeight = 184;
		
		AnchorPane.setLeftAnchor(myUpgradePickerView, inset);
		AnchorPane.setRightAnchor(myUpgradePickerView, inset + 2);
		AnchorPane.setTopAnchor(myUpgradePickerView, inset);
		//AnchorPane.setBottomAnchor(myUpgradePickerView, prefHeight*1.85);
		
		myUpgradePickerView.setPrefHeight(prefHeight);
		AnchorPane.setLeftAnchor(myGridPane, inset);
		AnchorPane.setRightAnchor(myGridPane, inset + 2);
		AnchorPane.setBottomAnchor(myGridPane, inset);
		AnchorPane.setTopAnchor(myGridPane, 
				(myUpgradePickerView.getPrefHeight()+2*inset));
		if(myCurrentActorData!=null){
			addDataViews(myCurrentActorData);
		}
		//myGridPane.prefHeightProperty().bind(this.heightProperty().add();
		
		UIHelper.setBackgroundColor(myGridPane, CustomColors.BLUE_200);
		UIHelper.setBackgroundColor(myUpgradePickerView, CustomColors.BLUE_200);
		UIHelper.setDropShadow(myGridPane);
		UIHelper.setDropShadow(myUpgradePickerView);
		this.getChildren().addAll(myGridPane, myUpgradePickerView);
	} 
	
	public void setLineageData(LineageData lineageData){
		myLineageData = lineageData;
		setActorData(lineageData);
	}
	
	private void setActorData(LineageData lineageData){
		ActorData first = lineageData.getProgenitor();
		System.out.println("ActorInfoView.setActorData: "+ first.getName());
		myCurrentActorData = lineageData.getProgenitor();
		myDataViews.clear();
		myGridPane.getChildren().clear();
		myUpgradePickerView.getChildren().clear(); 
		setupAddGenButton();
		List<ActorData>actors = new ArrayList<>(lineageData.getMap().values());
		StackPane actorButton=null;
		for(int i=0; i<actors.size(); i++){
			String path =actors.get(i).getImagePath();
			System.out.println("PATH: " + path);
			StackPane temp = setupImageView(new Image(path), actors.get(i));
			if(i==0){
				actorButton=temp;
			}
		}
		selectActorData(first);
		if(actorButton!=null){
			highlightCurrentActor(actorButton);
		}
	}
	
	private void addDataViews(ActorData first){
		addDataView(first.getHealth());
		for(Data d: first.getMyData()){
			addDataView(d);
		}
	}
	
	private void highlightCurrentActor(StackPane targetButton){
		for(StackPane button: myActors)
			button.setOpacity(.5);
		targetButton.setOpacity(1);
	}
	
	private void selectActorData(ActorData actorData){
		selectActorData(actorData, null);
	}
	
	private void selectActorData(ActorData actorData, ImageView view){
		System.out.println("ActorInfoView.selectActorData: "+ actorData.getName() +
				" : size=" + actorData.getMyData().size());
		printCurrent();
		myCurrentActorData = actorData;
		myDataViews.clear();
		myGridPane.getChildren().clear();
		if(view!=null){
			myActorImageView = view;
			myActorImageView.setImage(new Image(actorData.getImagePath()));
		}
		addDataViews(actorData);
	}
	
	private void addDataView(Data data){
		DataView view = new DataView(myGameData, data, this, 
				Arrays.asList(this.myActorTypeOptions.toArray(new BasicActorType[0])));
		int col = myDataViews.size()%GRID_X_DIM;
		int row = myDataViews.size() - col;
		myDataViews.add(view);
		double inset = 12;
		view.prefWidthProperty().bind(this.widthProperty().divide(3).add(-inset*(GRID_X_DIM + 1)/GRID_X_DIM));
		view.prefHeightProperty().bind(view.prefWidthProperty());
		GridPane.setMargin(view, new Insets(8));
		myGridPane.add(view, col, row);
	}
	
	private void setupAddNewClassButton(){
		ImageView img = new ImageView(new Image("add_icon_w.png"));
		img.setFitHeight(36);
		img.setFitWidth(36);
		StackPane button = UIHelper.buttonStack(e -> didClickNewClassButton(), Optional.ofNullable(null), Optional.of(img), Pos.CENTER, true);
		AnchorPane.setBottomAnchor(button, 16.0);
		AnchorPane.setRightAnchor(button, 19.0);
		button.setPrefHeight(56);
		button.setPrefWidth(56);
		UIHelper.setBackgroundColor(button, Color.rgb(0, 0, 0, 0.4));
		this.getChildren().add(button);
	}
	
	private void didClickNewClassButton(){
		if(myOptionPickerView == null)
			myOptionPickerView = new DataSelectionView(this);
		AnchorPane.setBottomAnchor(myOptionPickerView, 8.0);
		AnchorPane.setTopAnchor(myOptionPickerView, 8.0);
		AnchorPane.setRightAnchor(myOptionPickerView, 8.0);
		AnchorPane.setLeftAnchor(myOptionPickerView, 8.0);
		UIHelper.addNodeToPaneWithAnimation(this, myOptionPickerView);
	}
	
	/**
	 * MARK: -DataViewDelegate
	 */
	@Override
	public void setData(Data newData) {
		System.out.println("HERE");
		newData.addData(myCurrentActorData);
	}

	@Override
	public void didClickDelete(DataView dataView) {
		ScaleTransition sc = new ScaleTransition(Duration.seconds(0.3));
		sc.setNode(dataView);
		sc.setToX(0);
		sc.setToY(0);
		sc.play();
		sc.setOnFinished(e -> {
			this.myGridPane.getChildren().remove(dataView);
			this.myCurrentActorData.removeData(dataView.getData());
			this.myDataViews.remove(dataView);
			});
	}

	/**
	 * MARK: -OptionPickerDelegate
	 */
	
	@Override
	public void didPickOptionWithData(String dataName) {
		System.out.println("HERE" + dataName);
		Data d = DataGenerator.makeData(dataName+"Data");
		d.addData(this.myCurrentActorData);
		addDataView(d);
	}

	@Override
	public void didClickClose() { 
		UIHelper.removeNodeFromPaneWithAnimation(this, myOptionPickerView);
	}

	public void setActorTypeOptions(Set<BasicActorType> keySet) {
		this.myActorTypeOptions = keySet;
	}

	

}
