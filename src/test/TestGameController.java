package test;

import gameengine.controllers.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestGameController extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GameController controller = new GameController(null, null);
		controller.start(primaryStage, 0, 0, null);
	}
}
