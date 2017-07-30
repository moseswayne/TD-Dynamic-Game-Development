package ui.authoring.actor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import builders.infogen.AuthorInfoGenerator;
import gamedata.FieldData;
import gamedata.compositiongen.Data;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import ui.Preferences;
import ui.authoring.delegates.OptionPickerDelegate;
import ui.general.CustomColors;
import ui.general.UIHelper;

/**
 * Allows the user to pick 
 * @author TNK
 *
 */
public class DataSelectionView extends GridPane{
	
	private final int GRID_X_DIM = 3;
	
	private OptionPickerDelegate myDelegate;
	private static Map<String, List<FieldData>> OPTIONS = AuthorInfoGenerator.getPropertyTypesWithArgs();
	
	public DataSelectionView(OptionPickerDelegate delegate){
		super();
		myDelegate = delegate;
		setupViews();
	}


	private void setupViews() {
		UIHelper.setBackgroundColor(this, CustomColors.BLACK_GRAY);
		setupOptions();
	}


	private void setupOptions() {
		int count = 0;
		for(Entry<String, List<FieldData>> entry: OPTIONS.entrySet()){
			int col = count%GRID_X_DIM;
			int row = count - col;
			OptionView view = new OptionView(entry.getKey());
			view.setPrefHeight(56);
			widthProperty().addListener(e -> view.setPrefWidth(widthProperty().get()/3));
			GridPane.setMargin(view, new Insets(12.0));
			view.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> didPickOption(view));
			Tooltip.install(
				    view,
				    new Tooltip(AuthorInfoGenerator.getDescription(entry.getKey()))
				);
			this.add(view, col, row);
			count++;
		}
	}


	private void didPickOption(OptionView view){
		myDelegate.didPickOptionWithData(view.getDataName());
		myDelegate.didClickClose();
	}
	
	
}

final class OptionView extends Pane{
	private String dataName;
	public OptionView(String dataName){
		this.dataName = dataName;
		Label label = new Label(dataName);
		label.setFont(Preferences.FONT_SMALL_BOLD);
		label.setTextFill(Color.WHITE);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setAlignment(Pos.CENTER);
		this.widthProperty().addListener(e -> label.setMinWidth(this.widthProperty().get()));
		this.heightProperty().addListener(e -> label.setPrefHeight(this.getHeight()));
		this.getChildren().add(label);
		UIHelper.setBackgroundColor(this, CustomColors.BLUE_500);
		UIHelper.setBackgroundColor(label, CustomColors.BLUE_800);
	}
	public String getDataName(){
		return dataName;
	}
}
