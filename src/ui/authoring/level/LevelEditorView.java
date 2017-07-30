package ui.authoring.level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import util.PropertyUtil;

import gamedata.ActorData;
import gamedata.GameData;
import gamedata.LevelData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import types.BasicActorType;
import ui.authoring.PopupSize;
import ui.authoring.delegates.PopViewDelegate;
import ui.general.UIHelper;

/**
 * Represents the right hand bar in the authoring environment.
 * Dictates the list of Levels and add level button. 
 * Allows access to WaveChooserMenu and LevelEditorMenu
 * by clicking on the Level buttons.
 * 
 * @author maddiebriere
 * @author alex blumenstock
 */

public class LevelEditorView extends VBox{
	private static final String LEVEL_OPTIONS = "ui/authoring/resources/level_options";
	//# of Enemies
	//TODO: remove duplicated code from LeftPaneView, potentially by making methods static 
	private int level;
	private PopViewDelegate myDelegate;
	private GameData myData;
	private boolean isLoaded;
	
	public LevelEditorView(PopViewDelegate delegate, GameData data){
		super();
		this.setAlignment(Pos.CENTER);
		this.myData = data;
		myDelegate=delegate;
		isLoaded = data.getLevels().size()!=0;
		
		level=1;
		if(!isLoaded){
			initialSetup();
		} else{
			reload();
		}
		addLevelButton();
	
	}
	
	private void addLevelButton(){
		String levAdd = PropertyUtil.getTerm(LEVEL_OPTIONS, "AddLevel");
		StackPane newLevel = UIHelper.buttonStack(e->addNewLevel(), 
				Optional.of(LevelUtil.labelForStackButton(levAdd)), 
				Optional.of(LevelUtil.imageForStackButton("add_icon.png")),
				Pos.CENTER_RIGHT, true);
		newLevel.setPrefHeight(56);
		VBox.setMargin(newLevel, new Insets(8));

		this.getChildren().add(newLevel);
	}
	
	private void initialSetup(){
		StackPane levelOne = nextNewLevel();
		this.getChildren().add(levelOne);
	}
	
	private void reload(){
		System.out.println("RELOADING: " + myData.getLevels().size());
		for(Integer level: new ArrayList<Integer>(myData.getLevels().keySet())){
			this.getChildren().add(nextSavedLevel());
		}	
	}
	
	private void addNewLevel(){
		this.getChildren().add(this.getChildren().size()-1, nextNewLevel());
	}
	
	private StackPane nextSavedLevel(){
		return setupLevel();
	}
	
	private StackPane nextNewLevel(){
		LevelData newLevel = new LevelData();
		myData.getLevels().put(level, newLevel);
		return setupLevel();
		
	}
	
	private StackPane setupLevel(){
		ImageView img = LevelUtil.imageForStackButton("pencil.png");
		UIHelper.setDropShadow(img);
		img.setFitWidth(32);
		img.setFitHeight(32);
		String lvl = PropertyUtil.getTerm(LEVEL_OPTIONS, "Level");
		Label label = LevelUtil.labelForStackButton(String.format(lvl + " %d",level));
		int localLevel = level;
		img.setOnMousePressed(e->launchLevelEditor(localLevel));
		label.setOnMousePressed(e->launchWaveChooser(localLevel));
		StackPane nextLevel= UIHelper.buttonStack(e->{},  
				Optional.of(label), 
				Optional.ofNullable(img),Pos.CENTER_RIGHT, true);
		level++;
		nextLevel.setPrefHeight(56);
		VBox.setMargin(nextLevel, new Insets(8));
		return nextLevel;
	}
	
	private void launchLevelEditor(int localLevel){
		LevelData current = myData.getLevel(localLevel);
		LevelEditorMenu lem  = new LevelEditorMenu(myDelegate, current, localLevel);
		myDelegate.openViewWithSize(lem, PopupSize.SMALL);
	}
	
	private void launchWaveChooser(int level){
		LevelData current = myData.getLevel(level);
		Collection<ActorData> enemies = (myData.getAllOfType(new BasicActorType("Troop"))).values();
		WaveChooserMenu wcm  = new WaveChooserMenu(myDelegate, enemies, current, isLoaded);
		myDelegate.openViewWithSize(wcm, PopupSize.MEDIUM);
	}

}
