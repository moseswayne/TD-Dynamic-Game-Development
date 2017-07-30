package ui.authoring.level;


import java.util.ArrayList;
import java.util.List;

import builders.infogen.AuthorInfoGenerator;
import builders.objectgen.ConditionGenerator;
import gamedata.LevelData;
import gameengine.conditionsgen.Condition;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import ui.authoring.actor.Picker;
import ui.authoring.delegates.PopViewDelegate;
import ui.general.CustomColors;
import ui.general.UIHelper;
import util.PropertyUtil;

/**
 * Allows user to set level-specific variables
 * (e.g., health multiplier)
 * 
 * @author maddiebriere
 */

public class LevelEditorMenu extends AnchorPane {
	private static final String LEVEL_OPTIONS = "ui/authoring/resources/level_options";
	
	PopViewDelegate myDelegate;
	private int levelNum;
	private LevelData myData;
	ScrollPane settings;
	
	public LevelEditorMenu(PopViewDelegate delegate, LevelData level, int levelNum) {
		super();
		this.levelNum = levelNum;
		myDelegate = delegate;
		myData = level;
		setupViews();
		populateViews();
	}
	
	private void populateViews(){

		LevelUtil.setupBackButton(myDelegate, this);
		setupFields();
	}
	
	private String fieldCheck(double field){
		if(field==-1){
			return ""+1.0; 
		}
		else{
			return ""+field;
		}
	}
	
	private void setupFields(){
		String level = PropertyUtil.getTerm(LEVEL_OPTIONS,  "Level"); 
		String durSt = "             " + PropertyUtil.getTerm(LEVEL_OPTIONS, "Duration");
		
		String attSt = PropertyUtil.getTerm(LEVEL_OPTIONS, "Attack");
		String heaSt = PropertyUtil.getTerm(LEVEL_OPTIONS, "Health");
		String speSt = PropertyUtil.getTerm(LEVEL_OPTIONS, "Speed");
		
		
		Label title = LevelUtil.labelForTitle(level + " " + levelNum);
		title.setMinWidth(50);
		
		String dur = fieldCheck(myData.getDuration());
		String att = fieldCheck(myData.getAttackMultiplier());
		String spe = fieldCheck(myData.getSpeedMultiplier());
		String hea = fieldCheck(myData.getHealthMultiplier());
		
		HBox duration = generateEntry(durSt, dur, (o,oldText,newText) -> 
			this.updateDuration((String)newText));
		HBox attack= generateEntry(attSt, att, (o,oldText,newText) -> 
			this.updateAttack((String)newText));
		HBox health = generateEntry(heaSt, hea, (o,oldText,newText) -> 
			this.updateHealth((String)newText));
		HBox speed = generateEntry(speSt, spe, (o,oldText,newText) -> 
		this.updateSpeed((String)newText));
		
		VBox root = new VBox();
		//VBox.setMargin(root)
		root.getChildren().add(title);
		addClickableCondition(root, myData.getCondition());
		
		AnchorPane.setBottomAnchor(root, 0.0);
		AnchorPane.setTopAnchor(root, 36.);
		AnchorPane.setLeftAnchor(root, 0.0);
		AnchorPane.setRightAnchor(root, 0.0);
		
		
		root.getChildren().addAll(duration, attack, health, speed);
		root.setAlignment(Pos.CENTER);
		settings.setContent(root);
	}
	
	private void addClickableCondition(VBox vbox, Condition condition){
		List<String> conditions = new ArrayList<String>(AuthorInfoGenerator.getConditionTypesWithArgs().keySet());
		String name = AuthorInfoGenerator.getName(condition);
		Picker<String> input = addClickableField(vbox, "Win on", name, conditions);
		input.getTypeProperty().addListener(e -> {
			didEditCondition(input.getTypeProperty().get());
		});
	}
	
	private void didEditCondition(String newCondition){
		Condition condition = ConditionGenerator.makeCondition(newCondition);
		if(condition!=null){
			myData.setCondition(condition);
		}
	}
	
	private <T extends Object> Picker<T> addClickableField(VBox vbox,
			String nameKey, T value, List<T> types) {
		AnchorPane content = new AnchorPane();

		Label fieldName = new Label(nameKey + ":");
		fieldName.setTextFill(CustomColors.BLUE_800);
		fieldName.setTextAlignment(TextAlignment.CENTER);
		AnchorPane.setLeftAnchor(fieldName, 4.0);
		AnchorPane.setTopAnchor(fieldName, 4.0);
		AnchorPane.setBottomAnchor(fieldName, 4.0);
		fieldName.setPrefWidth(116);
		content.getChildren().add(fieldName);
		
		Picker <T> input = 
				new Picker<T>(value, types, true);
		AnchorPane.setRightAnchor(input, 4.0);
		AnchorPane.setTopAnchor(input, 4.0);
		AnchorPane.setBottomAnchor(input, 4.0);
		AnchorPane.setLeftAnchor(input, fieldName.getMaxWidth());
		
		UIHelper.setBackgroundColor(content, CustomColors.BLUE_200);
		content.getChildren().add(input);
		VBox.setMargin(content, new Insets(12.0, 18.0, 12.0, 18.0));
		vbox.getChildren().add(content);
		
		return input;
	}

	
	private void updateDuration(String newText){
		try{
			double duration = Double.parseDouble(newText);
			myData.setDuration(duration);
		}catch(Exception e){
			//No response
		}
	}
	
	private void updateAttack(String newText){
		try{
			double attack = Double.parseDouble(newText);
			myData.setAttackMultiplier(attack);
		}catch(Exception e){
			//No response
		}
	}
	
	private void updateHealth(String newText){
		try{
			double health = Double.parseDouble(newText);
			myData.setHealthMultiplier(health);
		}catch(Exception e){
			//No response
		}
	}
	
	private void updateSpeed(String newText){
		try{
			double speed = Double.parseDouble(newText);
			myData.setSpeedMultiplier(speed);
		}catch(Exception e){
			//No response
		}
	}
	
	private HBox generateEntry(String name, String value, ChangeListener listen){
		TextField field = LevelUtil.addField(value);
		field.textProperty().addListener(listen);
		field.setPrefWidth(116);
		HBox box = LevelUtil.generateHBox();
		box.getChildren().addAll(LevelUtil.labelForStackButtonBlue(name), field);
		VBox.setMargin(box, new Insets(12.0, 18.0, 12.0, 18.0));
		return box;
	}

	private void setupViews() {
		settings = new ScrollPane();
		settings.setHbarPolicy(ScrollBarPolicy.NEVER);
		settings.setVbarPolicy(ScrollBarPolicy.NEVER);
		setupBack(settings);
	}
	
	private void setupBack(ScrollPane pane){
		double inset = 12.0;
		LevelUtil.setVerticalAnchors(inset, pane);
		LevelUtil.setupBar(inset, 1.65, pane, this, 3 / 2);
		this.getChildren().add(pane);
	}

}
