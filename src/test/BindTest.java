package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.GUIBindingUtil;

public class BindTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox myBox = new VBox();
		Button button1 = new Button("1");
		Button button2 = new Button("2");
		Button button3 = new Button("3");
		Button button4 = new Button("4");
		Button button5 = new Button("5");
		GUIBindingUtil.bindVisisble(button2, null);
		GUIBindingUtil.bindVisisble(button4, null);
		myBox.getChildren().addAll(button1,button2,button3,button4,button5);
		Scene myScene = new Scene(myBox,300,300);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
