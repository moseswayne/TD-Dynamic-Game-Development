package ui.authoring.level;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import ui.Preferences;
import ui.authoring.delegates.PopViewDelegate;
import ui.general.CustomColors;
import ui.general.ImageButton;
import ui.general.UIHelper;
import util.Location;

/**
 * Util class holding methods shared across multiple
 * level editor windows.
 * 
 * @author maddiebriere
 *
 */

public class LevelUtil {

	public static ImageView imageForStackButton(String imagePath){
		Image img = new Image(imagePath);
		ImageView imageView = new ImageView(img);
		imageView.setFitWidth(40);
		imageView.setPreserveRatio(true);
		return imageView;
	}
	
	public static Label labelForStackButton(String title){
		Label lbl = new Label(title);
		lbl.setTextFill(CustomColors.GREEN_100);
		lbl.setFont(Preferences.FONT_SMALL_BOLD);
		return lbl;
	}
	
	public static Label labelForStackButtonBlue(String title){
		Label lbl = new Label(title);
		lbl.setTextFill(CustomColors.BLUE_800);
		lbl.setFont(Preferences.FONT_SMALL_BOLD);
		return lbl;
	}
	
	public static Label labelForTitle(String title){
		Label lbl = new Label(title);
		lbl.setTextFill(CustomColors.GREEN_900);
		lbl.setFont(Preferences.FONT_BIG_BOLD);
		return lbl;
	}
	
	public static void setupBackButton(PopViewDelegate delegate, AnchorPane pane) {
		ImageButton b = new ImageButton("back_icon.png", new Location(30., 30.));
		AnchorPane.setTopAnchor(b, 4.0);
		AnchorPane.setLeftAnchor(b, 4.0);
		b.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				e -> delegate.closeView(pane));
		pane.getChildren().add(b);
	}
	
	public static HBox generateHBox(int spacing){
		HBox root=new HBox();
		root.setSpacing(10);
		root.setAlignment(Pos.CENTER);
		return root;
	}
	
	public static HBox generateHBox(){
		return generateHBox(5);
	}
	
	public static void setupBar(double inset, double size, ScrollPane pane, AnchorPane parentPane, double multiply){
		AnchorPane.setLeftAnchor(pane, inset);
		
		AnchorPane.setRightAnchor(pane, 48.0);
		
		pane.setBackground(new Background(new BackgroundFill(CustomColors.BLUE_50,null,null)));
		pane.setStyle("-fx-background: #" + UIHelper.colorToHex(CustomColors.BLUE_50) + ";");

		UIHelper.setDropShadow(pane);
		pane.prefHeightProperty()
			.bind(parentPane.heightProperty().divide(size).subtract(inset * multiply));
	}
	
	public static void setVerticalAnchors(double inset, ScrollPane pane){
		AnchorPane.setTopAnchor(pane, inset);
		AnchorPane.setBottomAnchor(pane, inset);
	}
	
	public static void setVerticalAnchors(double inset, ScrollPane bottomSide, ScrollPane topSide){
		AnchorPane.setTopAnchor(topSide, inset);
		AnchorPane.setBottomAnchor(bottomSide, inset);
	}
	
	public static TextField addField(String value){
		StackPane lblWrapper = new StackPane();
		TextField field = new TextField(value);
		field.setPrefWidth(150);
		field.setFont(Preferences.FONT_MEDIUM);
		field.setAlignment(Pos.CENTER);
		field.setBackground(UIHelper.backgroundForColor(CustomColors.BLUE_200));
		field.setStyle("-fx-text-fill-color: #FFFFFF");
		field.setStyle("-fx-background-color: #" +UIHelper.colorToHex(CustomColors.BLUE_200) + ";");
		lblWrapper.getChildren().add(field);
		return field;
	}
	
}
