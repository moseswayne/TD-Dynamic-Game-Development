package ui.general;

import java.util.List;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class StackButton extends StackPane {
	
	private Optional<Node> centerNode = Optional.ofNullable(null);
	private Optional<Node> iconNode = Optional.ofNullable(null);
	
	public StackButton(Node centerNode, Node iconNode){
		this();
		setBothNodes(centerNode, iconNode);
	}

	public StackButton(){
		super();
		UIHelper.setBackgroundColor(this, CustomColors.INDIGO); //default color
	}
	
	public void setIconInsets(Insets insets){
		iconNode.ifPresent(o -> StackPane.setMargin(o, insets));
		
	}
	public void setCenterInsets(Insets insets){
		centerNode.ifPresent(o ->StackPane.setMargin(o, insets));
		
	}
	public void setBothNodes(Node centerNode, Node iconNode) {
		setCenterNode(centerNode);
	}
	
	public void setCenterNode(Node center){
		if(centerNode!= null){
			this.getChildren().remove(centerNode);
		}
		StackPane.setAlignment(center, Pos.CENTER);
		setIconInsets(new Insets(8, 8, 8, 48)); //magic number
		this.getChildren().add(center);
		centerNode = Optional.of(center);

	}
	
	public void setIconNode(Node icon){
		if(iconNode != null){
			this.getChildren().remove(iconNode);
		}
		StackPane.setAlignment(icon, Pos.CENTER_LEFT);
		setCenterInsets(new Insets(8.0));
		this.getChildren().add(icon);
		iconNode = Optional.of(icon);

	}
	
	public void setMouseEvent(EventHandler<MouseEvent> event){
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
	}
	
	public void setShadow(){
		UIHelper.setDropShadow(this);
	}

	public void setHeight(double height){
		this.setPrefHeight(height);
	}

}
