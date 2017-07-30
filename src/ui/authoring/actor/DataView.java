package ui.authoring.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Optional;

import builders.infogen.AuthorInfoGenerator;
import builders.objectgen.DataGenerator;
import gamedata.ActorData;
import gamedata.GameData;
import gamedata.PathData;
import gamedata.StringToFieldFactory;
import gamedata.compositiongen.Data;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import types.BasicActorType;
import ui.Preferences;
import ui.authoring.delegates.DataViewDelegate;
import ui.general.CustomColors;
import ui.general.UIHelper;

/**
 * 
 * Data view for data box in actor creation
 * 
 * @author talhakoc
 * @author maddiebriere
 *
 */

public class DataView extends AnchorPane {
	
	private PathData myPaths;
	private GameData myGame;
	private Data myData;
	private String myDataClassName;
	private VBox vbox;
	private Map<String, Object> myFields;
	private List<BasicActorType> myActorTypes;
	private DataViewDelegate myDelegate;
	
	public DataView(GameData game, Data data, DataViewDelegate delegate,  List<BasicActorType> actorTypes){
		super();
		UIHelper.setBackgroundColor(this, CustomColors.BLUE_50);
		myActorTypes = actorTypes;
		myData = data;
		myDataClassName = data.getClass().getSimpleName();
		myFields = AuthorInfoGenerator.getFields(data);
		myDelegate = delegate;
		myPaths = game.getMyPaths();
		myGame = game;
		
		setupViews();
		addFields();
	}

	private void addFields() {
		for(Entry<String, Object> entry: myFields.entrySet()){
			addField(entry.getKey(), entry.getValue());
		}
		
	}
	
	private void setupViews() {
		Label name = new Label(myDataClassName.replaceAll("Data", ""));
		name.setTextFill(CustomColors.BLUE_800);
		name.setFont(Preferences.FONT_MEDIUM_BOLD);
		name.setAlignment(Pos.CENTER);
		name.setPrefHeight(24);
		AnchorPane.setTopAnchor(name, 4.0);
		AnchorPane.setLeftAnchor(name, 4.0);
		AnchorPane.setRightAnchor(name, 4.0);
		UIActorUtil.colorByType(myDataClassName, name);
		
		vbox = new VBox();
		AnchorPane.setTopAnchor(vbox, 8.0 + name.getPrefHeight());
		AnchorPane.setLeftAnchor(vbox, 4.0);
		AnchorPane.setRightAnchor(vbox, 4.0);
		AnchorPane.setBottomAnchor(vbox, 4.0);
		
		ImageView img = new ImageView(new Image("clear_icon.png"));
		img.setFitHeight(16);
		img.setFitWidth(16);
		StackPane remove = UIHelper.buttonStack(e -> myDelegate.didClickDelete(this), 
				Optional.ofNullable(null), 
				Optional.of(img), Pos.CENTER, true);
		UIHelper.setBackgroundColor(remove, Color.TRANSPARENT);
		AnchorPane.setTopAnchor(remove, -12.0);
		AnchorPane.setRightAnchor(remove, -12.0);
		
		this.getChildren().addAll(name,vbox,remove);		
	}
	
	private void removeView(AnchorPane toClose){
		
	}
	
	@SuppressWarnings("unchecked")
	private void addField(String nameKey, Object value){
		Class<?> clazz = value.getClass();
		System.out.println(clazz);
		if(clazz == double.class ||clazz == Integer.class ||clazz == Double.class || clazz == int.class){
			//TODO: Make this not awful
			if(clazz == Integer.class && nameKey.equals("myProjectile"))
				addClickableActorField(nameKey, (Integer) value);
			else
				addTextField(nameKey, value);
		} else if(clazz == BasicActorType.class){
			addClickableTypeField(nameKey, (BasicActorType) value);
		} else if(clazz == List.class || clazz == ArrayList.class){
			if(myDataClassName.equals("MoveWithSetPathData"))
				addIntegerList(new ArrayList<Integer>(myPaths.getMyPaths().keySet()), nameKey);
			else
				addCategoryList(this.myActorTypes, nameKey);
		} else if(clazz == String.class){
			addTextField(nameKey, value);
		}
		
	}

	private void addLabel(AnchorPane content, String nameKey){
		Label fieldName = new Label(nameKey);
		fieldName.setTextFill(CustomColors.BLUE_800);
		fieldName.setAlignment(Pos.CENTER);
		AnchorPane.setLeftAnchor(fieldName, 4.0);
		AnchorPane.setTopAnchor(fieldName, 4.0);
		AnchorPane.setRightAnchor(fieldName, 4.0);
		fieldName.setPrefHeight(28);
		content.getChildren().add(fieldName);
	}
	
	private void addSelectionView(AnchorPane content, ListSelectionView<?> input){
		AnchorPane.setTopAnchor(input, 28.0);
		AnchorPane.setLeftAnchor(input, 4.0);
		AnchorPane.setBottomAnchor(input, 4.0);
		AnchorPane.setRightAnchor(input, 4.0);
		content.getChildren().add(input);
	}
	
	private void format(AnchorPane content){
		UIHelper.setBackgroundColor(content, CustomColors.BLUE_200);
		VBox.setMargin(content, new Insets(8.0));
		vbox.getChildren().add(content);
	}
	
	private <T extends Object> void addCategoryList(List<T> list, String nameKey) {
		AnchorPane content = new AnchorPane();
		addLabel(content, nameKey);
		
		ListSelectionView<T> input = 
				new ListSelectionView<T>(list);
		addSelectionView(content, input);
		input.getTypeList().addListener(e -> {
			didEditList(input.getTypeList().get(), nameKey);
		});
		
		format(content);
	}
	
	private void addIntegerList(List<Integer> list, String nameKey) {
		AnchorPane content = new AnchorPane();
		addLabel(content, nameKey);
		
		ListSelectionView<Integer> input = 
				new ListSelectionView<Integer>(list);
		addSelectionView(content, input);
		input.getTypeList().addListener(e -> {
			didEditIntegerList(input.getTypeList().get(), nameKey);
		});
		
		format(content);
	}

	
	private void addClickableTypeField(String nameKey, BasicActorType value){
		Picker<BasicActorType> input = addClickableField(nameKey, value, this.myActorTypes);
		input.getTypeProperty().addListener(e -> {
			didEditClickable(input.getTypeProperty().get(),nameKey);
		});
	}
	
	private void addClickableActorField(String nameKey, Integer option){
		ActorData myActor = myGame.getOption(option);
		Picker<ActorData> input = addClickableField(nameKey, myActor, 
				new ArrayList<>(myGame.getOptions().values()));
		input.getTypeProperty().addListener(e -> {
			didEditProjectileClickable(input.getTypeProperty().get(), nameKey);
		});
	}

	private <T extends Object> Picker<T> addClickableField(String nameKey, T value, List<T> types) {
		AnchorPane content = new AnchorPane();

		Label fieldName = new Label(nameKey + ":");
		fieldName.setTextFill(CustomColors.BLUE_800);
		fieldName.setTextAlignment(TextAlignment.CENTER);
		AnchorPane.setLeftAnchor(fieldName, 4.0);
		AnchorPane.setTopAnchor(fieldName, 4.0);
		AnchorPane.setBottomAnchor(fieldName, 4.0);
		fieldName.setMaxWidth(80);
		content.getChildren().add(fieldName);
		
		Picker <T> input = 
				new Picker<T>(value, types, true);
		AnchorPane.setRightAnchor(input, 4.0);
		AnchorPane.setTopAnchor(input, 4.0);
		AnchorPane.setBottomAnchor(input, 4.0);
		AnchorPane.setLeftAnchor(input, fieldName.getMaxWidth());
		
		UIHelper.setBackgroundColor(content, CustomColors.BLUE_200);
		content.getChildren().add(input);
		VBox.setMargin(content, new Insets(8.0));
		vbox.getChildren().add(content);
		
		return input;
	}

	private void addTextField(String nameKey, Object value){
		if(value.getClass().equals(String.class)){
			addStringField(nameKey, value);
		} else if(value.getClass().equals(Double.class) || value.getClass().equals(Integer.class)){
			addNumberField(nameKey, value);
		}
	}
	
	private TextField generateField(String nameKey, Object value){
		AnchorPane content = new AnchorPane();
		
		Label fieldName = new Label(nameKey + ":");
		fieldName.setTextFill(CustomColors.BLUE_800);
		fieldName.setTextAlignment(TextAlignment.CENTER);
		AnchorPane.setLeftAnchor(fieldName, 4.0);
		AnchorPane.setTopAnchor(fieldName, 4.0);
		AnchorPane.setBottomAnchor(fieldName, 4.0);
		fieldName.setMaxWidth(80);
		content.getChildren().add(fieldName);

		TextField field = new TextField(value.toString());
		field.setStyle("-fx-text-fill-color: #FFFFFF");
		field.setStyle("-fx-background-color: #" +UIHelper.colorToHex(CustomColors.BLUE_200) + ";");
		field.setAlignment(Pos.CENTER);
		//IMPORTANT: makes field number only and also updates the value of field
		
		AnchorPane.setRightAnchor(field, 4.0);
		AnchorPane.setTopAnchor(field, 4.0);
		AnchorPane.setBottomAnchor(field, 4.0);
		AnchorPane.setLeftAnchor(field, fieldName.getMaxWidth());
		
		UIHelper.setBackgroundColor(content, CustomColors.BLUE_200);
		content.getChildren().add(field);
		VBox.setMargin(content, new Insets(8.0));
		vbox.getChildren().add(content);
		
		return field;
	}
	
	private void addStringField(String nameKey, Object value){
		TextField field = generateField(nameKey, value);
		field.textProperty().addListener((e,oldVal,newValue) -> didEditField(field, nameKey, oldVal, newValue));
	}
	
	private void addNumberField(String nameKey, Object value){
		TextField field = generateField(nameKey, value);
		field.textProperty().addListener((e,oldVal,newValue) -> didEditNumberField(field, nameKey, oldVal, newValue));
		
	}
	
	private void didEditNumberField(TextField field, String varName, String oldValue, String newValue){
		 if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
			 field.setText(oldValue);
		 } else{
			didEditField(field,varName, oldValue, newValue);
		 }
	}
	
	private void didEditField(TextField field, String varName,String oldValue, String newValue){
        	System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\t");
        	System.out.println("\tADDING NEW DATA TO ACTORDATA");
     		this.myFields.put(varName, 
     				StringToFieldFactory.getObject(newValue, myFields.get(varName).getClass())
     				);
     		
     		Data d = DataGenerator.makeData(myDataClassName, myFields.values().toArray());
     		printMyData();
     		setMyData(d); 
     		System.out.println("*\t*\t*\t*\t*\t*\t*\t*\t\n");
	}
	private <T extends Object> void didEditClickable(T basicActorType, String varName){
   	 	System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\t");
   	 	System.out.println("\tADDING NEW DATA TO ACTORDATA");
		this.myFields.put(varName, 
				basicActorType
 				);
		Data d = DataGenerator.makeData(myDataClassName, myFields.values().toArray());
 		printMyData();
 		setMyData(d); 
 		System.out.println("*\t*\t*\t*\t*\t*\t*\t*\t\n");
	} 
	
	private void didEditProjectileClickable(ActorData actor, String varName){
   	 	System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\t");
   	 	System.out.println("\tADDING NEW DATA TO ACTORDATA");
   	 	Integer option = myGame.getOptionKey(actor);
		this.myFields.put(varName, option);
		Data d = DataGenerator.makeData(myDataClassName, myFields.values().toArray());
 		printMyData();
 		setMyData(d); 
 		System.out.println("*\t*\t*\t*\t*\t*\t*\t*\t\n");
	} 

	private <T extends Object> void didEditList(List<T> list, String nameKey) {
   	 	System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\t");
   	 	System.out.println("\tADDING NEW DATA TO ACTORDATA");
		this.myFields.put(nameKey, 
				list
 				);
		Data d = DataGenerator.makeData(myDataClassName, myFields.values().toArray());
 		printMyData();
 		setMyData(d); 
 		System.out.println("*\t*\t*\t*\t*\t*\t*\t*\t\n");
	}
	
	private void didEditIntegerList(List<Integer> list, String nameKey){
		System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\t");
   	 	System.out.println("\tADDING NEW DATA TO ACTORDATA");
   	 	List<List<Grid2D>> toPlace = list.stream()
   	 			.map(p -> myPaths.getPathByIndex(p))
   	 			.collect(Collectors.toList());
		this.myFields.put(nameKey, 
				toPlace);
		Data d = DataGenerator.makeData(myDataClassName, myFields.values().toArray());
		printMyData();
 		setMyData(d); 
 		System.out.println("*\t*\t*\t*\t*\t*\t*\t*\t\n");
	}
	
	private void printMyData(){
		System.out.println("className: "+myDataClassName +"\tfieldsClass: ");
 		for(Object o : myFields.values()){
 			System.out.println("\t-value:" + o + "\ttype:"+o.getClass().getSimpleName());
 		}
	}

	public Data getMyData() {
		return myData;
	}

	public void setMyData(Data newData) {
		myDelegate.setData(newData);
	}

	public Data getData() {
		return this.myData;
	}
	
}


