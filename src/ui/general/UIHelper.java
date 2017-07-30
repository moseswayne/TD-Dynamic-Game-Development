package ui.general;

import java.awt.Event;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 * A class that provides static methods for general purpose UI components
 * 
 * @author TNK
 *
 */
public class UIHelper {
	
	/**
	 * Sets the background color of a pane to the given color
	 * 
	 * @param pane Pane that the color is gonna be applied to
	 * @param paint Color
	 */
	public static void setBackgroundColor(Region pane, Paint paint){
		pane.setBackground(backgroundForColor(paint));
	}
	
	public static Background backgroundForColor(Paint paint){
		return new Background(new BackgroundFill[] { new BackgroundFill(paint, new CornerRadii(3.5), null)});
	}
	
	/**
	 * Adds drop shadow to a node.
	 * @param node
	 */
	public static void setDropShadow(Node node){
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		node.setEffect(dropShadow);
		
	}
	
	/**
	 * Adds click animation to a node.
	 * Node grows on mouse press and 
	 * shrinks on mouse release.
	 * 
	 * @param node
	 */
	public static void addClickAnimation(Node node){
		ScaleTransition s = new ScaleTransition();
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			s.stop();
			s.setToX(1.2);
			s.setToY(1.2);
			s.play();
		});
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			s.stop();
			s.setToX(1);
			s.setToY(1);
			s.play();
		});
		s.setDuration(Duration.seconds(0.2));
		s.setNode(node);
		s.setAutoReverse(true);

	}
	
	/**
	 * This is a general purpose purpose alternative to the Button object.
	 * It allows for an icon and text to be displayed next to each other
	 * @param event: The action that is executed when the button is clicked
	 * @param optionalIcon: if you include an imageview,  make sure to set the layout properties
	 * @param optionalLabel: also make sure to set the layout for this. 
	 * @return a StackPane that is substituted for a button in order to allow an icon and text
	 * the StackPane also has a default animation when clicked
	 */
	public static StackPane buttonStack(EventHandler<MouseEvent> event, 
			Optional<Node> optionalTextNode,Optional<ImageView> optionalIcon, Pos iconPos, boolean addDropShadow){
		StackPane view = new StackPane();
		UIHelper.setBackgroundColor(view, CustomColors.INDIGO); //default color
		view.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
		UIHelper.addClickAnimation(view);
		optionalIcon.ifPresent(img -> {
			view.getChildren().add(img);
			StackPane.setAlignment(img, iconPos);
			StackPane.setMargin(img, new Insets(8));
		});
		optionalTextNode.ifPresent(lbl -> {
			double inset = optionalIcon.isPresent()? optionalIcon.get().getFitWidth() + 16 : 0;
			if(iconPos.equals(Pos.CENTER_RIGHT))
				StackPane.setMargin(lbl, new Insets(8,inset,8,8));
			else if(iconPos.equals(Pos.CENTER_LEFT))
				StackPane.setMargin(lbl, new Insets(8,8,8,inset));
			view.getChildren().add(lbl);
			});
		if(addDropShadow)
			setDropShadow(view);
		return view;
	}

	public static String colorToHex(Color color) {
		double r = 255*color.getRed();
		double g = color.getGreen()*255;
		double b = color.getBlue()*255;
		return Integer.toHexString((int)r) + Integer.toHexString((int)g) + Integer.toHexString((int)b);
	}
	
	public static void addNodeToPaneWithAnimation(Pane parent, Node child){
		ScaleTransition sc = new ScaleTransition(Duration.seconds(0.3));
		sc.setNode(child);
		child.setScaleX(0);
		child.setScaleY(0);
		sc.setToX(1);
		sc.setToY(1);
		parent.getChildren().add(child);
		sc.play();
	}
	public static void removeNodeFromPaneWithAnimation(Pane parent, Node child){
		ScaleTransition sc = new ScaleTransition(Duration.seconds(0.3));
		sc.setNode(child);
		child.setScaleX(1);
		child.setScaleY(1);
		sc.setToX(0);
		sc.setToY(0);
		sc.play();
		sc.setOnFinished(e -> parent.getChildren().remove(child));
	}
	
	public static String printMap(Map<?,?> map){
		return Arrays.toString(map.entrySet().toArray());
	}
	
	public static void addPressAndHoldHandler(Node node, Duration holdTime, 
            EventHandler<MouseEvent> handler) {

        class Wrapper<T> { T content ; }
        Wrapper<MouseEvent> eventWrapper = new Wrapper<>();

        PauseTransition holdTimer = new PauseTransition(holdTime);
        holdTimer.setOnFinished(event -> handler.handle(eventWrapper.content));


        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            eventWrapper.content = event ;
            holdTimer.playFromStart();
        });
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> holdTimer.stop());
        node.addEventHandler(MouseEvent.DRAG_DETECTED, event -> holdTimer.stop());
    }
	
}
