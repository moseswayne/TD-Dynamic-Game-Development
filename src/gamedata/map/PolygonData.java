package gamedata.map;

import java.util.ArrayList;
import java.util.List;

import gameengine.grid.interfaces.Identifiers.Grid2D;
import ui.authoring.map.layer.UIPoint;
import util.Location;

/**
 * @author TNK
 * @author Anh
 *
 */
public class PolygonData {
	
	private List<Grid2D> myPoints;
	
	public PolygonData(List<Grid2D> points){
		myPoints = points;
	}
	
	/**
	 * default polygon: the whole square screen 
	 */
	public PolygonData(){
		myPoints = new ArrayList<>(); 
		myPoints.add(new Location(0.0,0.0));
		myPoints.add(new Location(0.0,1.0));
		myPoints.add(new Location(1.0,1.0));
		myPoints.add(new Location(1.0,0.0));
	}
	
	
	public List<Grid2D> getMyPoints(){
		return myPoints;
	}
	
	public String toString(){
		String s = "";
		for(Grid2D l : myPoints)
			s += l + ", ";
		return s;
	}
}
