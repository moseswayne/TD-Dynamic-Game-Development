package ui.player.inGame;

import java.util.Optional;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import ui.player.XStreamFileChooser;


/**
 * Creates a pane for settings elements such as returning back to main, changing
 * volume, etc
 * 
 * @author anngelyque
 */
public class SettingsPane extends SlidingPane {

	private GenericGameScreen ggs;
	private Hyperlink backToLogin;
	private double paneWidth = 150.;
	private String helpText;
	public static final String howToPlay = "data/resource/howToPlay.txt";

	public void setReturnToMain(EventHandler<ActionEvent> value) {
		backToLogin.setOnAction(value);
	}

	public SettingsPane(GenericGameScreen ggs, Optional<String> backImage, double slideTo,
			Optional<String> helpFileName) {
		super(backImage, slideTo);
		this.ggs = ggs;
		this.helpText = helpFileName.orElse(howToPlay);
		setup();
	}

	private void setup() {
		setupPane();
		addHelp();
		addInputHandle();
	}

	private void addInputHandle() {
//		IOUtil util = new IOUtil();
//		getVBox().getChildren().add(util.showMenu());
	}

	private void setupPane() {
		this.setStyle("-fx-background-color: MediumAquamarine;" + " -fx-border-radius: 0 10 10 0;"
				+ "-fx-background-radius: 0 10 10 0;");
		this.setPrefWidth(paneWidth);
		this.setId("settings");
	}

	private void addHelp() {
		Hyperlink helpLink = new Hyperlink("Help");
		helpLink.setOnAction(e -> showHelp());
		backToLogin = new Hyperlink("Return to Main");
		getVBox().getChildren().addAll(backToLogin, helpLink);
		this.getChildren().add(getVBox());
		getVBox().setAlignment(Pos.CENTER_LEFT);
	}

	public void addObject(Node object) {
		getVBox().getChildren().add(object);
	}

	private void showHelp() {
		HBox helpHBox = new HBox();
		helpHBox.setStyle("-fx-background-color: white; -fx-opacity: .7");
		helpHBox.setAlignment(Pos.CENTER);
		helpHBox.setPrefSize(420, 380);
		Text help = new Text(addHelpMessage(helpText));
		help.setStyle("-fx-font-size: 20");
		help.setTextAlignment(TextAlignment.CENTER);
		help.setWrappingWidth(400);
		helpHBox.getChildren().add(help);
		ggs.getChildren().add(helpHBox);
		AnchorPane.setTopAnchor(helpHBox, 25.);
		AnchorPane.setLeftAnchor(helpHBox, 25.);
		exit(helpHBox);
	}

	private void exit(Pane n) {
		EventHandler<MouseEvent> close = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ScaleTransition st = new ScaleTransition(Duration.millis(1000), n);
				st.setByX(-1f);
				st.setByY(-1f);
				st.play();
			}
		};
		OptionButton exit = new OptionButton(0, "", "x_icon.png", close);
		exit.getButton().setStyle("-fx-background-color: transparent");
		n.getChildren().add(exit.getButton());
	}

	private String addHelpMessage(String file) {
		XStreamFileChooser f = new XStreamFileChooser(file);
		return f.readInClass();
	}
}
