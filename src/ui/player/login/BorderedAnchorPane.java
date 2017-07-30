package ui.player.login;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ui.player.inGame.OptionButton;

public class BorderedAnchorPane {

	private Pane root;
	private BorderPane borderPane;
	private OptionButton back;
	
	public Pane getRoot() {
		return root;
	}
	
	public BorderPane getBorderPane() {
		return borderPane;
	}
	
	public OptionButton getBackButton() {
		return back;
	}
	
	public BorderedAnchorPane() {
		this.root = new AnchorPane();
		this.borderPane = new BorderPane();
		root.getChildren().add(borderPane);
		anchor();
		addBackButton();
	}
	
	private void anchor(){
		AnchorPane.setBottomAnchor(borderPane, 0.0);
		AnchorPane.setLeftAnchor(borderPane, 0.0);
		AnchorPane.setRightAnchor(borderPane, 0.0);
		AnchorPane.setTopAnchor(borderPane, 0.0);
	}
	
	private void addBackButton() {
		back = new OptionButton(0, "", "back_icon.png", e -> {});
		root.getChildren().add(back.getButton());
		AnchorPane.setLeftAnchor(back.getButton(), 20.);
		AnchorPane.setTopAnchor(back.getButton(), 20.);
	}
	
	
}
