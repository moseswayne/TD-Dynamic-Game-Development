package ui.player.inGame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import gamedata.ActorData;
import gameengine.grid.interfaces.frontendinfo.FrontEndInformation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ui.Preferences;
import ui.general.ImageViewPane;
import ui.handlers.AnimationHandler;
import ui.handlers.LoginHandler;
import ui.handlers.UIHandler;
import ui.player.login.LoginElement;
import util.InsufficientMoneyException;
import util.LayerNotPlaceableException;
import util.VoogaException;
import util.observerobservable.VoogaObserver;

public class GameScreen extends GenericGameScreen
		implements VoogaObserver<Map<Integer, FrontEndInformation>>, LoginElement {

	private ImageViewPane ivp;
	private UIHandler uihandler;
	private SimpleHUD hud;
	private Map<Integer, Actor> actorsMap;
	private ScreenHandler screenHandler;
	private LoginHandler loginhandler;
	private AnimationHandler animationhandler;

	public GameScreen(LoginHandler loginHandler, UIHandler uihandler, AnimationHandler animationHandler,
			Supplier<SimpleHUD> simpleHUD) {
		super(uihandler, Optional.ofNullable(null), Optional.ofNullable(null),
				Optional.of(uihandler.getDisplayData().getBackgroundImagePath()));
		this.uihandler = uihandler;
		this.animationhandler = animationHandler;
		this.actorsMap = new HashMap<Integer, Actor>();
		this.loginhandler = loginHandler;
		this.ivp = this.getIVP();
		hud = simpleHUD.get();
		initializeScreenHandler();
		setup();
		fadeTransition(this, .0, 1.);
	}

	public void setLoginHandler(LoginHandler loginhandler) {
		this.loginhandler = loginhandler;
	}

	public void notifyWin() {
		notifyStatus("You won!");
	}

	public void notifyLose() {
		notifyStatus("You lost!");
	}

	private void notifyStatus(String status) {
		animationhandler.stop();
		BoxBlur blur = new BoxBlur();
		blur.setWidth(Preferences.SCREEN_WIDTH);
		blur.setHeight(Preferences.SCREEN_HEIGHT);
		blur.setIterations(1);
		getChildren().forEach(child -> {
			child.setEffect(blur);
			child.setMouseTransparent(true);
		});

		Text msg = new Text(status);
		msg.setStyle("-fx-font-size: 50; -fx-fill: black");
		VBox holder = new VBox();
		holder.getChildren().add(msg);

		Hyperlink returnLink = new Hyperlink("Return To Main");
		returnLink.setOnAction(returnToMain());
		returnLink.setStyle("-fx-font-size: 25; -fx-fill: blue");
		holder.getChildren().add(returnLink);

		holder.setAlignment(Pos.CENTER);
		holder.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(200.))));
		getChildren().add(holder);
		setupAnchors(holder, 20.);
	}

	private void initializeScreenHandler() {
		screenHandler = new ScreenHandler() {
			@Override
			public Actor createActor(double x, double y, int option, ActorData actorData) {
				Actor actor = new Actor(uihandler, screenHandler, option, actorData);
				actor.getMainPane().setLayoutX(getWidth() - x);
				actor.getMainPane().setLayoutY(y);
				getChildren().add(actor.getMainPane());
				return actor;
			}

			@Override
			public void showError(String msg) {
				Text error = new Text(msg);
				error.setStyle("-fx-font-size: 50; -fx-fill: red");
				HBox holder = new HBox(error);
				holder.setAlignment(Pos.CENTER);
				getChildren().add(holder);
				FadeTransition ft = (FadeTransition) fadeTransition(holder, 1.0, 0.);
				ft.setOnFinished(e -> getChildren().remove(holder));
				setupAnchors(holder, 20.);
			}

			@Override
			public void showUpgrades(Integer option, Actor oldActor) {
				HBox upgrades = new HBox();
				uihandler.getLineageData().get(option).upgrade();
				EventHandler<MouseEvent> close = new EventHandler<MouseEvent>()  {
					@Override
					public void handle(MouseEvent event) {
						ScaleTransition st = new ScaleTransition(Duration.millis(1000), upgrades);
						st.setByX(-1f);
						st.setByY(-1f);
						st.play();	
					}
				};
				EventHandler<MouseEvent> upgradeActor = new EventHandler<MouseEvent>()  {
					@Override
					public void handle(MouseEvent event) {
						try {
							getChildren().remove(actorsMap.get(oldActor.getID()).getMainPane());
							actorsMap.remove(oldActor.getID());
							uihandler.deleteGameObject(oldActor.getID());
							Actor actor = new Actor(uihandler, screenHandler, option, uihandler.getLineageData().get(option).getCurrent());
							actor.getMainPane().setLayoutX(oldActor.getMainPane().getLayoutX());
							actor.getMainPane().setLayoutY(oldActor.getMainPane().getLayoutY());
							getChildren().add(actor.getMainPane());
							actor.turnOffHandlers();
							Integer actorID = uihandler.addGameObject(option, actor.getMainPane().getLayoutX() / getWidth(), actor.getMainPane().getLayoutY() / getWidth());
							actorsMap.put(actorID, actor);
							System.out.println(actorsMap);
							actor.setID(actorID);
							close.handle(event);
							actor.getMainPane().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> showUpgrades(option, actor));
						} catch (VoogaException | LayerNotPlaceableException | InsufficientMoneyException e) {
							e.printStackTrace();
						}
					}
				};
				OptionButton exit = new OptionButton(0, "", "x_icon.png", close);
				OptionButton upgradedActor = new OptionButton(0, "", uihandler.getLineageData().get(option).getCurrent().getImagePath(), upgradeActor);
				upgrades.getChildren().addAll(upgradedActor.getButton(), exit.getButton());
				AnchorPane.setBottomAnchor(upgrades, 100.);
				getChildren().add(upgrades);
			}
			
			@Override
			public void addActorToMap(int id, Actor actor) {
				if (actorsMap.get(id) == null)
					actorsMap.put(id, actor);
			}

			@Override
			public void deleteActorFromScreen(int id) {
				getChildren().remove(actorsMap.get(id).getMainPane());
			}

			@Override
			public boolean isActorInMap(int id) {
				return actorsMap.get(id) != null;
			}

			@Override
			public double getWidth() {
				return ivp.getWidth() - 2 * ivp.getImageInsets().x;
			}

			@Override
			public double getHeight() {
				return ivp.getHeight() - 2 * ivp.getImageInsets().y;
			}
		};
	}

	private void setupAnchors(Node n, double value) {
		AnchorPane.setTopAnchor(n, value);
		AnchorPane.setLeftAnchor(n, value);
		AnchorPane.setRightAnchor(n, value);
		AnchorPane.setBottomAnchor(n, value);
	}

	private void setup() {
		setupPanels();
		setupHUD();
		setReturnToMain(returnToMain());
	}

	private void setupPanels() {
		SidePanel sidePanel = new SidePanel(screenHandler, uihandler.getOptions());
		AnchorPane.setRightAnchor(sidePanel.getSidePane(), 10.0);
		this.getChildren().add(sidePanel.getSidePane());
		addInternalPanesToRoot(sidePanel.getListOfPanes());
	}

	public void addInternalPanesToRoot(Collection<OptionsPane> listOfPanes) {
		listOfPanes.forEach(op -> {
			this.getChildren().add(op);
			AnchorPane.setRightAnchor(op, -op.getPrefWidth());
			op.setStyle(("-fx-background-color: MediumAquamarine;" + " -fx-border-radius: 10 0 0 10;"
					+ "-fx-background-radius: 10 0 0 10;"));
		});
	}

	private void setupHUD() {
		AnchorPane.setBottomAnchor(hud.getGrid(), 10.);
		AnchorPane.setLeftAnchor(hud.getGrid(), 10.);
		this.getChildren().add(hud.getGrid());
	}

	private Transition fadeTransition(Node n, double from, double to) {
		FadeTransition ft = new FadeTransition(Duration.millis(1000), n);
		ft.setFromValue(from);
		ft.setToValue(to);
		ft.play();
		return ft;
	}

	private EventHandler<ActionEvent> returnToMain() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				animationhandler.stop();
				if (getMediaPlayer().getStatus().equals(Status.PLAYING)) {
					getMediaPlayer().stop();
				}
				loginhandler.returnToMain();
			}
		};
	}

	@Override
	public void update(Map<Integer, FrontEndInformation> arg) {
		actorsMap.keySet().removeIf(id -> {
			if (arg.containsKey(id)) {
				return false;
			}
			this.getChildren().remove(actorsMap.get(id).getMainPane());
			return true;
		});

		arg.keySet().stream().forEach(id -> {
			Integer actorOption = arg.get(id).getActorOption();
			Actor actor = null;
			if (!actorsMap.containsKey(id)) {
				actor = new Actor(uihandler, screenHandler, actorOption, uihandler.getOptions().get(actorOption));
				actorsMap.put(id, actor);
				this.getChildren().add(actor.getMainPane());
				actor.turnOffHandlers();
			} else {
				actor = actorsMap.get(id);
			}
			actor.setHealth(arg.get(id).getActorPercentHealth());
			double xCoor = util.Transformer.ratioToCoordinate(arg.get(id).getActorLocation().getX(),
					(ivp.getWidth() - ivp.getImageInsets().x));
			double yCoor = util.Transformer.ratioToCoordinate(arg.get(id).getActorLocation().getY(),
					(ivp.getHeight() - ivp.getImageInsets().y));
			
			actor.getMainPane().setLayoutX(xCoor - actor.getMainPane().getWidth()/2); // to get the right image position
			actor.getMainPane().setLayoutY(yCoor - actor.getMainPane().getWidth()/2 );
			
			//actor.getMainPane().setLayoutX(xCoor);
			//actor.getMainPane().setLayoutY(yCoor);
		});
	}
}