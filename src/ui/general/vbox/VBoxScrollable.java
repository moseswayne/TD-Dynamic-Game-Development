package ui.general.vbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ui.general.StackButton;
import ui.general.UIHelper;
import util.Tuple;

/**
 * A general purpose VBox that can be fed data and display them in a nice way
 * @author TNK
 *
 */
public class VBoxScrollable extends VBox {
	
	private List<StackButton> myData = new ArrayList<>();
	
	private Insets itemInsets;
	
	public VBoxScrollable(double vGap){
		this(vGap, VBoxStyle.SPACED);
	}
	public VBoxScrollable(double vGap, VBoxStyle style){
		super(vGap);
		setStyle(style);
	}

	
	public void setStyle(VBoxStyle style){
		switch(style){
		case SPACED:
			setInsetsSpaced(12.0);
			break;
		default:
			setInsetsEmpty();
			break;
		}
	}
	private void setInsetsEmpty(){
		itemInsets = new Insets(0.0);
	}
	private void setInsetsSpaced(double i){
		itemInsets = new Insets(i);
	}
	
	/**
	 * 
	 * @param data
	 */
	public void setCells(List<StackButton> buttons) {
		for(StackButton b : buttons){
			StackPane.setMargin(b, itemInsets);
			this.getChildren().add(b);
		}
	}



	
}
