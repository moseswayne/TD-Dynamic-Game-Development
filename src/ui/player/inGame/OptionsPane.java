package ui.player.inGame;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import gamedata.ActorData;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Creates a sliding pane of like actors i.e. Towers, Troops, ... given a map of options to actorData.
 * The pane is accessed through the given paneName (name links it to outside button clicked to reveal pane).
 * Event handler to create Actor (within GameScreen) when button is clicked.
 * @author anngelyque
 */
public class OptionsPane extends SlidingPane {

	private Map<Integer, ActorData> mapOfOptions;
	private ScreenHandler screenHandler;
	private String paneName;

	public String getPaneName() {
		return paneName;
	}

	public Map<Integer, ActorData> getMap() {
		return mapOfOptions;
	}

	public OptionsPane(ScreenHandler handler, Map<Integer, ActorData> map, String name, double width) {
		super(Optional.of("back_icon_flipped.png"), width);
		this.screenHandler = handler;
		this.mapOfOptions = map;
		this.paneName = name;
		this.setPrefWidth(width);
		addActorPane();
		setSlideTo(this.getPrefWidth());
	}

	/**
	 * Using the map of Option (Integer) to ActorData, a list of Buttons based on the category name i.e. Towers, Enemies, ... are added to a sliding pane
	 */
	private void addActorPane() {
		List<Button> listOfButtons = mapOfOptions.keySet().stream().map(entry -> new OptionButton(entry, 
				mapOfOptions.get(entry).getName(), mapOfOptions.get(entry).getImagePath(), pressed).getButton()).collect(Collectors.toList());
		this.getVBox().getChildren().addAll(listOfButtons);
		this.getChildren().add(this.getVBox());
	}

	/**
	 * When a button is clicked, the screenHandler is called to create the new (pane) actor on screen.
	 */
	EventHandler<MouseEvent> pressed = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent ME) {
			Object obj = ME.getSource();
			if (obj instanceof Button) {
				Integer id = Integer.parseInt(((Button) obj).getId());
				screenHandler.createActor(getPrefWidth() + ((Button) obj).getLayoutX(), ((Button) obj).getLayoutY(), id, mapOfOptions.get(id));
			}
		}
	};
}
