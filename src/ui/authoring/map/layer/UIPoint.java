package ui.authoring.map.layer;

import java.util.HashMap;
import java.util.Map;

import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ui.authoring.map.PointType;
import ui.general.CustomColors;

public class UIPoint extends Circle {
	
	@SuppressWarnings("serial")
	Map<PointType, Color> pointTypeToColor = new HashMap<PointType, Color>() {{
        put(PointType.ENTRY, CustomColors.INDIGO);
        put(PointType.PATH, CustomColors.GREEN_900);
        put(PointType.EXIT, CustomColors.AMBER);

        //etc
    }};
    
    private Grid2D myCompressedLocation;
    private Color myColor = CustomColors.GREEN_900;
    
    /**
     * 
     * @param gridLocation - the location of the mouse that was captured on click
     * @param size - the size of the parent node that holds the image
     * @param insets - use ImageViewPane's getInsets method to provide this
     */
	public UIPoint(Grid2D coordinates, double centerX, double centerY){
		super();
		setCenterX(centerX);
		setCenterY(centerY);
		myCompressedLocation = coordinates;
		setColor(myColor);
		setRadius(7);
	}

//	private Tuple<Double, Double> getImageSize(Tuple<Double, Double> size, Tuple<Double, Double> insets){
//		return new Tuple<Double, Double>(size.x - 2*insets.x, size.y - 2*insets.y);
//	}
	
//	private void updateLocation(Tuple<Double, Double> imageSize, Tuple<Double, Double> insets){
//		this.setCenterX(myCompressedLocation.getX()*imageSize.x + insets.x);
//		this.setCenterY(myCompressedLocation.getY()*imageSize.y + + insets.y);
//	}
//	private Coordinates compressLocation(Location location,Tuple<Double, Double> insets, Tuple<Double, Double> imageSize){
//		 return new Coordinates((location.getX() - insets.x)/imageSize.x, (location.getY() - insets.y)/imageSize.y);
//	}
//	
//	public void updateSize(Tuple<Double,Double> size, Tuple<Double,Double> insets){
//		updateLocation(getImageSize(size, insets),insets);
//	}
	
	public void setPointType(PointType pointType){
		setColor(pointTypeToColor.get(pointType));
	}
	
	public void setColor(Color c){
		myColor = c;
		setFill(c);
	}
	
	public Grid2D getCoordinates(){
		return this.myCompressedLocation;
	}
	
	@Override 
	public String toString(){
		return "(" +this.myCompressedLocation.getX() + "," + this.myCompressedLocation.getY()+")";
	}

}
