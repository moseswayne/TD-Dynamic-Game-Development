/**
 * 
 */
package ui.player.ratings;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ui.Preferences;

/**
 * @author harirajan
 *
 */
public class RatingStars extends HBox {
	
	private static final int STAR_SIZE = 20;
	private static final String EMPTY_STAR_IMG = "empty_star.png";
	private static final String FILLED_STAR_IMG = "filled_star.png";

	private List<ImageView> myStars;
	private int myRating;
	private ResourceBundle resource;

	public RatingStars(int rating, boolean editable, int totalstars, String lang) {
		super();
		resource = ResourceBundle.getBundle(lang);
		myRating = rating;
		Image img = new Image(getClass().getClassLoader().getResourceAsStream(EMPTY_STAR_IMG));
		myStars = new ArrayList<>();
		for (int i = 0; i < totalstars; i++) myStars.add(new ImageView(img));
		setUpStars(editable);
		fillStars(myRating - 1);
		if (editable) {
			Label lbl = new Label(resource.getString("ratingprompt"));
			lbl.setFont(Preferences.FONT_MEDIUM_BOLD);
			lbl.setTextFill(Color.WHITE);
			getChildren().add(lbl);
		}
		getChildren().addAll(myStars);
	}

	private void setUpStars(boolean editable) {
		for (int i = 0; i < myStars.size(); i++) {
			myStars.get(i).setFitHeight(STAR_SIZE);
			myStars.get(i).setFitWidth(STAR_SIZE);
			if (editable) {
				myStars.get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, new StarMouseHoverHandler(i));
				myStars.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, new StarMouseClickHandler(i));
				myStars.get(i).addEventHandler(MouseEvent.MOUSE_EXITED, new StarMouseExitHandler(i));
			}
		}
	}
	
	private void fillStars(int index) {
		for (int i = 0; i < myStars.size(); i++) {
			if (i <= index)
				myStars.get(i).setImage(new Image(getClass().getClassLoader().getResourceAsStream(FILLED_STAR_IMG)));
			else {
				myStars.get(i).setImage(new Image(getClass().getClassLoader().getResourceAsStream(EMPTY_STAR_IMG)));
			}
				
		}
	}
	
	public void clear() {
		myRating = 0;
		fillStars(-1);
	}
	
	private void restoreRating() {
		fillStars(myRating - 1);
	}
	
	public int getRating() {
		return myRating;
	}
	
	
	
	private abstract class StarMouseEventHandler implements EventHandler<MouseEvent> {
		
		protected int myIndex;
		
		public StarMouseEventHandler(int index) {
			myIndex = index;
		}
		
	}
	
	private class StarMouseHoverHandler extends StarMouseEventHandler {

		public StarMouseHoverHandler(int index) {
			super(index);
			// TODO Auto-generated constructor stub
		}

		/* (non-Javadoc)
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			fillStars(myIndex);
			
		}
	}
	
	private class StarMouseClickHandler extends StarMouseEventHandler {

		public StarMouseClickHandler(int index) {
			super(index);
			// TODO Auto-generated constructor stub
		}

		/* (non-Javadoc)
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			myRating = myIndex + 1;
			
		}
	}
	
	private class StarMouseExitHandler extends StarMouseEventHandler {

		public StarMouseExitHandler(int index) {
			super(index);
			// TODO Auto-generated constructor stub
		}

		/* (non-Javadoc)
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
		@Override
		public void handle(MouseEvent event) {
			restoreRating();
		}
	}
	

}
