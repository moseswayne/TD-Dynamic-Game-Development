package ui.player.users;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ui.player.login.DataEntryGrid;

public class GameStatsGrid extends DataEntryGrid{

	private Map<Text, TextField> entryMap;
	private Map<String, TextField> dataMap;
	private ResourceBundle profileR;
	
	public TextField getUsername() {
		return dataMap.get(profileR.getString("username"));
	}
	
	public TextField getScore() {
		return dataMap.get(profileR.getString("score"));
	}
	
	public TextField getLastPlayed() {
		return dataMap.get(profileR.getString("last"));
	}
	
	public TextField getMostPlayed() {
		return dataMap.get(profileR.getString("most"));
	}
	
	public GameStatsGrid(ResourceBundle resource) {
		super(resource);
		profileR = resource;
		entryMap = new LinkedHashMap<>();
		dataMap = new LinkedHashMap<>();
		addValues();
		addToGrid(entryMap);
	}
	
	@Override
	public Map<Text, TextField> getEntryMap() {
		return entryMap;
	}
	
	public void addValues(){
		addTextField(dataMap, entryMap, profileR.getString("username"), new TextField());
		addTextField(dataMap, entryMap, profileR.getString("score"), new TextField());
		addTextField(dataMap, entryMap, profileR.getString("last"), new TextField());
		addTextField(dataMap, entryMap, profileR.getString("most"), new TextField());
	}
}
