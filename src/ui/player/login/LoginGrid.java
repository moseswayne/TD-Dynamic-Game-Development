package ui.player.login;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginGrid extends DataEntryGrid{

	private Map<Text, TextField> entryMap;
	private Map<String, TextField> dataMap;
	private ResourceBundle loginResource;
	
	public TextField getUsername() {
		return dataMap.get(loginResource.getString("username"));
	}
	
	public TextField getPassword() {
		return dataMap.get(loginResource.getString("password"));
	}
	
	
	@Override
	public Map<Text, TextField> getEntryMap() {
		return entryMap;
	}
	
	public LoginGrid(ResourceBundle resource) {
		super(resource);
		loginResource = resource;
		entryMap = new LinkedHashMap<>();
		dataMap = new LinkedHashMap<>();
		addValues();
		addToGrid(entryMap);
	}
	
	public void addValues(){
		addTextField(dataMap, entryMap, loginResource.getString("username"), new TextField());
		addTextField(dataMap, entryMap, loginResource.getString("password"), new PasswordField());
	}
}
