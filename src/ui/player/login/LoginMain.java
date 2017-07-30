package ui.player.login;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import XML.xmlmanager.classes.XStreamSerializer;
import XML.xmlmanager.interfaces.serialization.VoogaSerializer;
import gamedata.GameData;
import gameengine.controllers.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Preferences;
import ui.authoring.AuthoringView;
import ui.handlers.LoginHandler;
import ui.player.GameSelector;
import ui.player.ProfileCornerPicture;
import ui.player.XStreamFileChooser;
import ui.player.leaderboard.LeaderboardView;
import ui.player.ratings.RatingView;
import ui.player.users.ProfileCard;
import ui.player.users.User;
import ui.player.users.UserDatabase;
import util.FileSelector;

/**
 * Acts as the primary controller for the UI. Houses all of the visual elements:
 * user authentication screens ({@link ui.player.login.Login Login} and
 * {@link ui.player.login.Signup Signup}), {@link ui.authoring.AuthoringView
 * AuthoringView}, {@link ui.player.GameSelector GameSelector},
 * {@link ui.player.ratings.RatingView RatingView}, and
 * {@link ui.player.inGame.GameScreen GameScreen}.
 * 
 * Navigation between screens and querying of the
 * {@link ui.player.users.UserDatabase UserDatabase} is handled by the
 * {@link ui.handlers.LoginHandler LoginHandler}.
 * 
 * @author Vishnu Gottiparthy
 *
 */
public class LoginMain {

	private Stage stage;
	private GameController gameController;
	private UserDatabase database;
	private ResourceBundle loginResource;
	private Login loginScreen;
	private Signup signupPage;
	private LoginHandler loginhandler;
	private String css;
	private String resource;
	public static final String userDatabase = "userDatabase.xml";
	public static final String CONFIG_EXTENSION = "*.xml";
	private static final String guestUser = "Guest";
	private static final List<String> games = new ArrayList<>(Arrays.asList("games/Duvall vs. Singletons.xml"));

	/**
	 * Initialized in {@link voogasalad_ilovesingletons.Main}
	 * 
	 * @param stage
	 *            Main window for the UI
	 * @param css
	 *            Filename describing CSS styling for the UI
	 * @param resource
	 *            Filename describing the resource file of all login dialog
	 *            messages
	 */
	public LoginMain(Stage stage, String css, String resource) {
		this.stage = stage;
		this.css = css;
		this.resource = resource;
		setupDatabase();
		setupLoginHandler();
		stage.setMinHeight(Preferences.SCREEN_HEIGHT);
		stage.setMinWidth(Preferences.SCREEN_WIDTH);
		loginResource = ResourceBundle.getBundle(resource);
		loginScreen = new Login(loginhandler, css, resource);
		Scene scene = loginScreen.getScene();
		stage.setScene(scene);
	}

	private void setupLoginHandler() {
		this.loginhandler = new LoginHandler() {
			@Override
			public User getActiveUser() {
				return database.getActiveUser();
			}

			@Override
			public void showProfile() {
				if (getActiveUser() != null) showProfileCard(getActiveUser());
			}

			@Override
			public Boolean login(String username, String password) {
				return database.getPasswords().login(username, password);
			}

			@Override
			public User findUser(String username) {
				for (User u : database.getDatabase()) {
					if (u.getUsername().equals(username)) {
						return u;
					}
				}
				return null;
			}

			@Override
			public void setActiveUser(User user) {
				database.setActiveUser(user);
			}

			@Override
			public void setCornerProfileCard(User user) {
				ProfileCornerPicture cornerCard = new ProfileCornerPicture(user.getProfilePicture(),
						e -> showProfileCard(user));
				loginScreen.getRoot().getChildren().add(cornerCard);
				AnchorPane.setRightAnchor(cornerCard, 15.);
				AnchorPane.setTopAnchor(cornerCard, 15.);
			}

			@Override
			public void returnToMain() {
				stage.setScene(loginScreen.getScene());
				stage.setTitle("Login");
				stage.setWidth(Preferences.SCREEN_WIDTH);
				stage.setHeight(Preferences.SCREEN_HEIGHT);
			}

			@Override
			public void gotoSignupPage() {
				signupPage = new Signup(loginhandler, database, loginResource, "signupScreen.css");
				stage.setScene(signupPage.getScene());
				stage.setTitle(loginResource.getString("signup"));
			}

			@Override
			public void gotoAuth() {
				AuthoringView view = new AuthoringView(loginhandler);
				stage.setScene(new Scene(view, Preferences.SCREEN_WIDTH, Preferences.SCREEN_HEIGHT, Color.WHITE));
			}

			@Override
			public void gotoGameSelector() {
				List<MiniGame> miniGamesList = new ArrayList<>();
				miniGamesList.add(new MiniGame("Load Custom Game", "black.jpg", e -> promptUserToChooseGame()));
				games.forEach(game -> {
					GameData gd = readGame(new File(game));
					miniGamesList.add(new MiniGame(gd.getName(), gd.getDisplayData().getBackgroundImagePath(), e -> goToGameScreen(gd)));
				});
				GameSelector select = new GameSelector(loginhandler, "English", "mainScreen.css", miniGamesList);
				stage.setScene(select.getScene());
				stage.setTitle("Game Selector");
				stage.show();
			}

			@Override
			public void gotoReviews() {
				stage.setScene(new RatingView(loginhandler, "English").getScene());
				stage.setWidth(Preferences.SCREEN_WIDTH);
				stage.setHeight(Preferences.SCREEN_HEIGHT);
			}

			@Override
			public void gotoLeaderboard() {
				stage.setScene(new LeaderboardView(loginhandler).getScene());
				stage.setWidth(Preferences.SCREEN_WIDTH);
				stage.setHeight(Preferences.SCREEN_HEIGHT);
			}

			@Override
			public Iterator<User> getUsersInExpOrder() {
				return database.getUsersInExpOrder();
			}
		};
	}

	private void setupDatabase() {
		XStream mySerializer = new XStream(new DomDriver());
		XStreamFileChooser fileChooser = new XStreamFileChooser(userDatabase);
		database = (fileChooser.readInClass() != null) ? (UserDatabase) mySerializer.fromXML(fileChooser.readInClass())
				: new UserDatabase();
	}

	private void showProfileCard(User user) {
		if (!user.equals(loginhandler.findUser(guestUser))) {
			ProfileCard card = new ProfileCard("profile", user, "profile.css");
			card.setLogoutAction(e -> {
				loginhandler.setActiveUser(loginhandler.findUser(guestUser));
				loginScreen = new Login(loginhandler, css, resource);
				loginhandler.returnToMain();
			});
			HBox hb = card.getCard();
			((Pane) stage.getScene().getRoot()).getChildren().add(hb);
			AnchorPane.setBottomAnchor(hb, 25.);
			AnchorPane.setLeftAnchor(hb, 25.);
		} else {
			new Alert(AlertType.ERROR, loginResource.getString("pleaselogin")).showAndWait();
		}
	}

	private void promptUserToChooseGame() {
		FileSelector mySelector = new FileSelector(CONFIG_EXTENSION);
		File dataFile = mySelector.open(new Stage());
		System.out.println("print");
		goToGameScreen(readGame(dataFile));
	}

	private GameData readGame(File dataFile) {
		try {	
			if (dataFile != null) {
				String XML = new String(Files.readAllBytes(Paths.get(dataFile.getAbsolutePath())));
				VoogaSerializer serializer = new XStreamSerializer();
				GameData gameData = serializer.makeObjectFromXMLString(XML, GameData.class);
				return gameData;
			}
		} catch (Exception e) {
			new Alert(AlertType.ERROR, "Invalid GameData file chosen").showAndWait();
		}
		return null;
	}

	private void goToGameScreen(GameData gameData) {
		gameController = new GameController(gameData, loginhandler);
		gameController.start(stage, Preferences.SCREEN_WIDTH, Preferences.SCREEN_HEIGHT, Color.WHITE);
		stage.setTitle("Game Screen");
	}
}