package ui.general.vbox;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemData {
	private Node centerNode;
	private Node iconNode;
	private EventHandler<MouseEvent> event;
	public ItemData(EventHandler<MouseEvent> event, Node centerNode, Node iconNode, Pos iconPos, double inset){
		this.centerNode = centerNode;
		this.iconNode = iconNode;
		this.event = event;
	}


	public Node getCenterNode() {
		return centerNode;
	}


	public void setCenterNode(Node centerNode) {
		this.centerNode = centerNode;
	}


	public Node getIconNode() {
		return iconNode;
	}


	public void setIconNode(Node iconNode) {
		this.iconNode = iconNode;
	}


	public EventHandler<MouseEvent> getEvent() {
		return event;
	}
	public void setEvent(EventHandler<MouseEvent> event) {
		this.event = event;
	}
	
}
