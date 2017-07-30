package ui.player.users;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ui.player.login.DataEntryGrid;

public class ExperienceGrid extends DataEntryGrid{

	private Map<Text, TextField> entryMap;
	private Map<String, TextField> dataMap;
	private ResourceBundle profileR;
	
	public TextField getExperience() {
		return dataMap.get(profileR.getString("experience"));
	}
	
	public TextField getLevel() {
		return dataMap.get(profileR.getString("level"));
	}
	
	public ExperienceGrid(ResourceBundle resource) {
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
		addTextField(dataMap, entryMap, profileR.getString("level"), new TextField());
		addTextField(dataMap, entryMap, profileR.getString("experience"), new TextField());
	}
}
