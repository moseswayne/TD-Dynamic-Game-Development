package ui.player.login;

import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public abstract class DataEntryGrid {

	GridPane grid = new GridPane();

	public abstract void addValues();

	public abstract Map<Text, TextField> getEntryMap();

	public GridPane getGrid() {
		return grid;
	}

	public DataEntryGrid(ResourceBundle resource) {
		setGridProperties();
	}

	public DataEntryGrid(Map<Text, TextField> entries) {
		setGridProperties();
	}

	private void setGridProperties() {
		grid.setHgap(10);
		grid.setVgap(10);
	}

	public void addTextField(Map<String, TextField> dataMap, Map<Text, TextField> entryMap, String s, TextField field) {
		TextField textfield = field;
		Text text = new Text(s);
		entryMap.put(text, textfield);
		dataMap.put(s, textfield);
		text.setId("text");
	}

	public void addToGrid(Map<Text, TextField> entries) {
		int i = 0; // col
		int k = 0; // row
		for (Map.Entry<Text, TextField> entry : entries.entrySet()) {
			grid.add(entry.getKey(), i, k);
			grid.add(entry.getValue(), i + 1, k);
			k++;
		}
	}
}
