package test.inputTests;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane myPane = new StackPane();
		Scene myScene = new Scene(myPane, 500, 500);
		//MultiInputHandler handler = new MultiInputHandler(myScene,"devices");
		//myPane.getChildren().add(handler.getMenu());
		myScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				System.out.println(event.getText());
			}
			
		});
		primaryStage.setScene(myScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
