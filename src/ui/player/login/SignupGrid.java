package ui.player.login;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SignupGrid extends DataEntryGrid{

	private Map<Text, TextField> entryMap;
	private Map<String, TextField> dataMap;
	private ResourceBundle loginResource;
	
	public TextField getUsername() {
		return dataMap.get(loginResource.getString("username"));
	}
	
	public TextField getPassword() {
		return dataMap.get(loginResource.getString("password"));
	}
	
	public TextField getRePassword() {
		return dataMap.get(loginResource.getString("repassword"));
	}
	
	public TextField getEmail() {
		return dataMap.get(loginResource.getString("email"));
	}
	
	public TextField getReEmail() {
		return dataMap.get(loginResource.getString("reemail"));
	}
	
	@Override
	public Map<Text, TextField> getEntryMap() {
		return entryMap;
	}
	
	public SignupGrid(ResourceBundle resource){
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
		addTextField(dataMap, entryMap, loginResource.getString("repassword"), new PasswordField());
		addTextField(dataMap, entryMap, loginResource.getString("email"), new TextField());
		addTextField(dataMap, entryMap, loginResource.getString("reemail"), new TextField());
	}
}