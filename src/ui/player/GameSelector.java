package ui.player;
import java.util.List;	
import java.util.Optional;
import java.util.ResourceBundle;

import ui.Preferences;
import ui.general.UIHelper;
import ui.handlers.LoginHandler;
import ui.player.inGame.OptionButton;
import ui.player.login.BorderedAnchorPane;
import ui.player.login.LoginElement;
import ui.player.login.MiniGame;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameSelector extends BorderedAnchorPane implements LoginElement {

	private Scene scene;
	private List<MiniGame> gamesList; 
	private OptionButton back;
	private double height = Preferences.SCREEN_HEIGHT;
	private double width = Preferences.SCREEN_WIDTH;
	private ResourceBundle resource;
	private ScrollPane gameMenu;
	private LoginHandler loginhandler;
	
	@Override
	public Scene getScene() {
		return scene;
	}
	
	public GameSelector(LoginHandler loginhandler, String lang, String css, List<MiniGame> gamesList2){
		this.loginhandler = loginhandler;
		this.gamesList = gamesList2;
		gameMenu = new ScrollPane();
		scene = new Scene(getRoot(), width, height);
		scene.getStylesheets().add(css);
		resource = ResourceBundle.getBundle(lang);
		setup();
	}
	
	private void setup() {
		setupLayout();
		setupTitle();
		setupScrollPane();
		setupSideArrows();
		setupBottom();
		addBackButton();
		getBackButton().getButton().setOnAction(e -> loginhandler.returnToMain());
	}

	private void addBackButton() {
		back = new OptionButton(0, "", "back_icon.png", e -> {});
		AnchorPane.setLeftAnchor(back.getButton(), 20.);
		AnchorPane.setTopAnchor(back.getButton(), 20.);
	}

	private void setupLayout(){
		gameMenu.getStyleClass().add("scroll-pane");
		getRoot().getStyleClass().add("anchor-pane");
	}
	
	private void setupTitle() {
		Label title = new Label(resource.getString("gameselector"));
		title.setId("title");
		BorderPane.setAlignment(title, Pos.CENTER);
		getBorderPane().setTop(title);
	}

	private void setupScrollPane(){
		HBox gamesHBox = new HBox(50);
		gamesHBox.setPadding(new Insets(0., 50., 0., 50.));
		gamesList.forEach(game -> {
			VBox vbox = new VBox(40);
			StackPane g = new StackPane();
			g.getStyleClass().add("image-stack-pane");
			g.getChildren().add(new ImageView(new Image(game.getImagePath(), 300, 250, false, true)));
			g.setOnMouseClicked(game.getClicked());
			Text name = new Text(game.getName());
			name.setId("text");
			vbox.getChildren().addAll(name, g);
			vbox.setAlignment(Pos.CENTER);
			gamesHBox.getChildren().add(vbox);
		});
		gameMenu.setContent(gamesHBox);
		gamesHBox.setAlignment(Pos.CENTER);
		gameMenu.setHbarPolicy(ScrollBarPolicy.NEVER);
		gameMenu.setVbarPolicy(ScrollBarPolicy.NEVER);
		gameMenu.setBackground(Background.EMPTY);
		gameMenu.setPadding(new Insets(15, 12, 15, 12));
		getBorderPane().setCenter(gameMenu);
		BorderPane.setAlignment(gamesHBox, Pos.CENTER);
	}
	
	private void setupSideArrows(){
         ImageView left = new ImageView(new Image("left_arrow.png", 50, 50, true, true));
         ImageView right = new ImageView(new Image("right_arrow.png", 50, 50, true, true));
         getBorderPane().setLeft(left);
         getBorderPane().setRight(right);
         right.setOnMousePressed(event -> moveScrollPane(.1));
         left.setOnMousePressed(event -> moveScrollPane(-.1));
         BorderPane.setAlignment(left, Pos.CENTER);
         BorderPane.setAlignment(right, Pos.CENTER);
	}
	
	private void moveScrollPane(double value) {
		final Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(gameMenu.hvalueProperty(), gameMenu.getHvalue() + value);
        final KeyFrame kf = new KeyFrame(Duration.millis(100), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
	}
	
	private void setupBottom(){
		StackPane profile = simpleImageStackPane("Profile", "profile_icon.png", e -> { profileClicked(); });
		StackPane help = simpleImageStackPane("Help", "splash_icon.png", e -> {});
		HBox hbox = new HBox(100);
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.getChildren().addAll(profile, help);
		hbox.setAlignment(Pos.CENTER);
		getBorderPane().setBottom(hbox);
	}
	
	private void profileClicked() {
		loginhandler.showProfile();
	}

	private StackPane simpleImageStackPane(String name, String image, EventHandler<MouseEvent> event){
		StackPane sp = UIHelper.buttonStack(event, 
				Optional.of(new Label(name)), 
				Optional.of(imageForStackButton(image)), 
				Pos.CENTER_RIGHT, true);
		sp.getStyleClass().add("stack-pane");
		return sp;
	}
	
	private ImageView imageForStackButton(String imagePath){
		ImageView iv = new ImageView(new Image(imagePath));
		iv.setFitWidth(30);
		iv.setPreserveRatio(true);
		return iv;
	}
}