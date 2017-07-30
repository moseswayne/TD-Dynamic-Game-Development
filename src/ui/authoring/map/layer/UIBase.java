package ui.authoring.map.layer;

import gamedata.ActorData;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ui.general.ImageViewPane;
import util.Location;
import util.Tuple;

/**
 * Purpose is to allow the user to modify individual entries
 * in the BasePlacementData. 
 * 
 * This class holds a reference to an entry in BasePlacementData
 * This class also automates the conversion between BasicData to
 * actual javafx data
 * 
 * IMPORTANT: pass a reference of   
 * 
 * @author TNK
 *
 */
public class UIBase extends ImageViewPane{
	
	private Tuple<ActorData, Coordinates> myData;
	
	public UIBase(ActorData data, Coordinates loc, Pane pane){
		
		setMyData(new Tuple<>(data, loc));
		
		//add image
		ImageView img = new ImageView(new Image(data.getImagePath()));
		img.setFitHeight(data.getBasic().getImageDimensions().getY()*pane.getHeight());
		img.setFitWidth(data.getBasic().getImageDimensions().getX()*pane.getWidth());
		img.setPreserveRatio(true);
		setImageView(img);
		
		updateLayout(pane);
		
	}

	
	/**
	 * updates layout based on information contained in myData 
	 * by decompressing points to fit the dimensions of the bounds
	 * @param parentBounds
	 */
	public void updateLayout(Pane pane){
		double xNorm = pane.getWidth();
		double yNorm = pane.getHeight();
		double width = xNorm*myData.x.getBasic().getImageDimensions().getX();
		double height = yNorm*myData.x.getBasic().getImageDimensions().getY();
		double x = myData.y.getX()*xNorm - width/2;
		double y = myData.y.getY()*yNorm - height/2;
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setPrefHeight(height);
		this.setPrefWidth(width);
		ImageView view = new ImageView(new Image(myData.x.getImagePath()));
		view.setPreserveRatio(true);
		setImageView(view);

	}
	
	/**
	 * centers base onto the specified location
	 * @param parentBounds bounds of parent is required
	 * @param location xy in terms of javafx
	 */
	public void updateLocationUI(Location loc){
		this.setLayoutX(loc.getX() - getWidth()/2.0);
		this.setLayoutY(loc.getY() - getHeight()/2.0);
		//System.out.println("loc,H,W: (" + loc +"), "+ height+", " + width);

	}
	
	public void updateLocationData(double x, double y){
		myData.y.setX(x);
		myData.y.setY(y);
	}

	public void setMyData(Tuple<ActorData, Coordinates> myData) {
		this.myData = myData;
	}


	public Tuple<ActorData, Coordinates> getData() {
		return this.myData;
	}

}
