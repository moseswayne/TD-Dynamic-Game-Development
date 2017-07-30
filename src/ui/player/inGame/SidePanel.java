package ui.player.inGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import gamedata.ActorData;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.GUIBindingUtil;

/**
 * Creates a pane containing buttons linking to the possible user options.
 * Upon click, the pane with the relative nodes will slide out.
 * 
 * @author anngelyque
 *
 */
public class SidePanel {

	private Pane sidePane;
	private VBox mainBox;
	private Map<String, String> iconImages;
	private List<OptionsPane> listOfPanes;
	private Map<Integer, ActorData> options;
	private ScreenHandler screenHandler;
	private Set<String> types;
	private Map<OptionButton, String> mapOfOptionButtons;
	
	private static final String panel = "panel.css";
	
	public Pane getSidePane(){
		return sidePane;
	}
	
	public Collection<OptionsPane> getListOfPanes(){
		return listOfPanes;
	}
	
	public SidePanel(ScreenHandler screenHandler, Map<Integer, ActorData> options) {
		this.screenHandler = screenHandler;
		this.options = options;
		this.iconImages = new HashMap<>();
		this.mapOfOptionButtons = new HashMap<>();
		this.listOfPanes = new ArrayList<>();
		this.mainBox = new VBox(20);
		this.sidePane = new AnchorPane();
		setup();
	}
	
	public void setup() {
		createInternalPanes();
		linkMainPaneToInternalPanes();
	}
	
	/**
	 * Creates the panes to link to their respective main buttons.
	 */
	private void createInternalPanes() {
		types = options.keySet().stream().map(option -> options.get(option).getType().toString()).collect(Collectors.toSet());
		types.forEach(type -> {
			Map<Integer, ActorData> map = new HashMap<>();
			options.keySet().forEach(option -> {
				if (options.get(option).getType().toString().equals(type) && options.get(option).getType().isPlaceable()) {
					map.put(option, options.get(option));
					if (!iconImages.containsKey(type)) iconImages.put(type, options.get(option).getImagePath());
				}
			});
			listOfPanes.add(getPane(map, type));
		});
	}
	
	/**
	 * Links the internal pane that exists off screen to the main button on screen.
	 * Upon click, the pane will slide out
	 * If pane is empty, no button will be generated / placed on screen
	 */
	private void linkMainPaneToInternalPanes() {
		mainBox.getStylesheets().add(panel);
		for (Map.Entry<String, String> entry : iconImages.entrySet()) {
			OptionButton optionButton = new OptionButton(0, "", entry.getValue(), openPane);
			mapOfOptionButtons.put(optionButton, entry.getKey());
			listOfPanes.stream().filter(pane -> pane.getPaneName().equals(entry.getKey())).forEach(pane -> GUIBindingUtil.bindVisisble(optionButton.getButton(), pane.getMap().keySet()));
			mainBox.getChildren().add(optionButton.getButton());
		}
		sidePane.getChildren().add(mainBox);
		mainBox.setAlignment(Pos.CENTER_RIGHT);
		
		AnchorPane.setRightAnchor(mainBox, 0.0);
		AnchorPane.setBottomAnchor(mainBox, 20.0);
		AnchorPane.setTopAnchor(mainBox, 20.0);
	}
	
	private OptionsPane getPane (Map<Integer, ActorData> map, String name) {
		OptionsPane optionPane = new OptionsPane(screenHandler, map, name, 115);
		optionPane.setPrefHeight(300);
		return optionPane;
	}
	
	EventHandler<MouseEvent> openPane = new EventHandler<MouseEvent>()  {
	    @Override
	    public void handle( final MouseEvent ME ) {
	    	Object obj = ME.getSource();
	    	if ( obj instanceof Button ) {
	    		for (OptionsPane optionsPane : listOfPanes) {
	    			for (Map.Entry<OptionButton, String> entry : mapOfOptionButtons.entrySet()) {
	    				if (entry.getKey().getButton().equals(((Button) obj)) && entry.getValue().equals(optionsPane.getPaneName())){
	    					new SlidingPane().slidePane(optionsPane, -optionsPane.getPrefWidth());
	    				}
	    			}
	    		}
	    	}
	    }
	};
}
