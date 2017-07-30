/**
 * 
 */
package ui.player.ratings;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ui.Preferences;
import ui.general.UIHelper;
import ui.handlers.LoginHandler;
import ui.player.login.LoginElement;

/**
 * @author harirajan
 *
 */
public class RatingView implements LoginElement {
	
	private static final String BACK_ICON = "back_icon.png"; 
	private static final int DEFAULT_BACK_BUTTON_SIZE = 50;
	private static final int TITLE_BAR_SPACING = 10;
	private static final Background DEFAULT_TITE_BACKGROUND = 
			new Background(new BackgroundFill(Color.LAVENDER, new CornerRadii(5), null));
	private Scene scene;
	private LoginHandler loginhandler;
	private ResourceBundle resource;
	
	public RatingView(LoginHandler loginhandler, String lang) {
		this.loginhandler = loginhandler;
		resource = ResourceBundle.getBundle(lang);
		ScrollPane sp = new ScrollPane();
		sp.setFitToWidth(true);
		
		RatingDisplay ratings = new RatingDisplay(lang);
		ratings.setAlignment(Pos.TOP_CENTER);
		sp.setContent(ratings);
		
		VBox box = new VBox();
		box.getChildren().add(setUpTopNode());
		box.getChildren().add(sp);
		scene = new Scene(box);
	}

	@Override
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * @return
	 */
	private Node setUpTopNode() {
		HBox hb = new HBox();
		hb.getChildren().add(setUpBackButton());
		hb.getChildren().add(setUpTitle());
		hb.setSpacing(TITLE_BAR_SPACING);
		hb.setAlignment(Pos.TOP_CENTER);
		return hb;
	}

	/**
	 * @return
	 */
	private Node setUpTitle() {
		Label lbl = new Label(resource.getString("ratingreviewtitle"));
		Button b = new Button();
		UIHelper.setDropShadow(b);
		UIHelper.setDropShadow(lbl);
		lbl.setFont(Preferences.FONT_BIG_BOLD);
		b.setGraphic(lbl);
		b.setBackground(DEFAULT_TITE_BACKGROUND);
		b.setMaxWidth(Preferences.SCREEN_WIDTH - DEFAULT_BACK_BUTTON_SIZE);
		b.setMinWidth(Preferences.SCREEN_WIDTH - DEFAULT_BACK_BUTTON_SIZE);
		b.setPrefWidth(Preferences.SCREEN_WIDTH - DEFAULT_BACK_BUTTON_SIZE);
		return b;
	}

	/**
	 * @return
	 */
	private Node setUpBackButton() {
		Button b = new Button();
		UIHelper.setDropShadow(b);
		ImageView backimg = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(BACK_ICON)));
		backimg.setFitHeight(DEFAULT_BACK_BUTTON_SIZE);
		backimg.setFitWidth(DEFAULT_BACK_BUTTON_SIZE);
		UIHelper.addClickAnimation(b);
		b.setBackground(DEFAULT_TITE_BACKGROUND);
		b.setGraphic(backimg);
		b.setOnAction(e -> loginhandler.returnToMain());
		return b;
	}
}
