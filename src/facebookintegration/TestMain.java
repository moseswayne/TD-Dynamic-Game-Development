package facebookintegration;

import facebookintegration.interfaces.MasterFacebookUser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * 
 * @author Gideon
 * 
 * This class is not a formal test. It is simply for showing a proof 
 * of concept that the facebook Authenticator is working. It can display
 * your profile picture and post to your wall
 */
public class TestMain extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Opens the test window and allows you to log in after clicking the button
	 * Will also post to your wall
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Get FB Set Up!");
        Button btn = new Button();
        btn.setText("Get FB Set Up!");
        StackPane root = new StackPane();
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
	            MasterFacebookUser user = new Authenticator();
	            user.authenticate();
                Image pic = user.getProfilePicture();
                ImageView IV = new ImageView(pic);
	            IV.setFitHeight(50);
	            IV.setFitWidth(50);
	            StackPane.setAlignment(IV, Pos.TOP_CENTER);
	            root.getChildren().add(IV);
	            user.postWithoutVoogaLink("what's up everyone");
            }
        });
       
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
	}

}
