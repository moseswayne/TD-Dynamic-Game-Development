package ui.player.inGame;

import java.util.Optional;

import gamedata.ActorData;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.general.UIHelper;
import ui.handlers.UIHandler;
import util.InsufficientMoneyException;
import util.LayerNotPlaceableException;
import util.VoogaException;

public class Actor {

	private UIHandler uihandler;
	private ScreenHandler screenhandler;
	private Actor clazz = this;
	private Pane mainPane;
	private Pane actor;
	private Integer option;
	private double width;
	private double height;
	private Optional<Boolean> removeable;
	private ProgressBar health;

	public Pane getMainPane() {
		return mainPane;
	}

	public void setHealth(double d) {
		health.setProgress(d);
	}

	public Integer getID() {
		return Integer.parseInt(actor.getId());
	}
	
	public void setID(Integer ID) {
		mainPane.setId(ID.toString());
	}
	
	public void turnOffHandlers() {
		mainPane.removeEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
		mainPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, place);
		mainPane.removeEventHandler(MouseEvent.MOUSE_RELEASED, released);
	}

	public Actor(UIHandler uihandler, ScreenHandler screenhandler, Integer option, ActorData actorData) {
		actor = UIHelper.buttonStack(e -> {}, Optional.ofNullable(null),
				Optional.of(new ImageView(new Image(actorData.getImagePath(), 30, 30, true, true))), Pos.CENTER, true);
		actor.setStyle("-fx-effect: null;");
		actor.setBackground(Background.EMPTY);
		this.screenhandler = screenhandler;
		this.option = option;
		this.width = screenhandler.getWidth();
		this.height = screenhandler.getHeight();
		this.uihandler = uihandler;
		this.removeable = Optional.of(false); // true if actors are allowed to
		setup();
	}

	public void setup() {
		setupHealth();
		setupEvents();
	}

	private void setupHealth() {
		health = new ProgressBar(1F);
		health.setPrefWidth(actor.getWidth());
		health.setScaleY(.2);
		VBox v = new VBox(-8);
		v.getChildren().addAll(actor, health);
		v.setAlignment(Pos.CENTER);
		mainPane = new Pane(v);
	}

	/**
	 * Creates events for when the node is dragged, released, or clicked
	 * secondarily
	 */
	public void setupEvents() {
		mainPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
		mainPane.addEventHandler(MouseEvent.MOUSE_CLICKED, place);
		mainPane.addEventHandler(MouseEvent.MOUSE_RELEASED, released);
	}

	EventHandler<MouseEvent> drag = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent ME) {
			mainPane.setLayoutX(ME.getSceneX());
			mainPane.setLayoutY(ME.getSceneY());
		}
	};

	/**
	 * if location is appropriate, actor will update to a new location when
	 * released from drag
	 */
	// TODO: how is off grid checked?
	EventHandler<MouseEvent> released = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent ME) {
			if (actor.getId() != null && (screenhandler.isActorInMap(Integer.parseInt(actor.getId())))) {
				try {
					uihandler.updateGameObjectLocation(Integer.parseInt(actor.getId()), mainPane.getLayoutX() / width,
							mainPane.getLayoutY() / height);
				} catch (NumberFormatException | VoogaException e) {
					screenhandler.showError("You cannot place an item there!");
				}
			}
		}
	};

	/**
	 * Upon secondary mouse click, actor will be placed in the actor map The new
	 * actor id is now the unique ID returned from the uihandler. Event handler
	 * is then removed optionally.
	 */
	EventHandler<MouseEvent> place = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent ME) {
			if (((MouseEvent) ME).getButton().equals(MouseButton.SECONDARY)) {
				try {
					Integer actorID = uihandler.addGameObject(option, mainPane.getLayoutX() / width,
								mainPane.getLayoutY() / height);
					Object obj = ME.getSource();
					if (obj instanceof Pane) {
						((Pane) obj).removeEventHandler(MouseEvent.MOUSE_CLICKED, place);
						((Pane) obj).addEventHandler(MouseEvent.MOUSE_CLICKED, upgrades);
						if (removeable.isPresent() && !removeable.get())
							((Pane) obj).removeEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
						screenhandler.addActorToMap(actorID, clazz);
						actor.setId(actorID.toString());
					}
				} catch (NumberFormatException | VoogaException | LayerNotPlaceableException e) {
					screenhandler.showError("Invalid Location!");
				} catch (InsufficientMoneyException e) {
					screenhandler.showError("Insufficient Funds!");
				}
			}

		}
	};
	EventHandler<MouseEvent> upgrades = new EventHandler<MouseEvent>() {
		@Override
		public void handle(final MouseEvent ME) {
			//screenhandler.showUpgrades(option, clazz);
		}
	};
	
	

}
