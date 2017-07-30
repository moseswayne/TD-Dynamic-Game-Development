package ui.authoring.map.layer;

import java.util.ArrayList;
import java.util.List;

import gamedata.map.PolygonData;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

public class UIPolygon extends Polygon{
	
	private PolygonData myPolygonData;
	
	public UIPolygon(PolygonData polygon, Pane pane){
		myPolygonData = polygon;
		reload(pane);
	}
	
	public void addPoint(Coordinates coordinate, Pane pane){
		myPolygonData.getMyPoints().add(coordinate);
		this.getPoints().add(coordinate.getX()*pane.getWidth());
		this.getPoints().add(coordinate.getY()*pane.getHeight());
	}
	
	public void reload(Pane pane){
		this.getPoints().clear();
		myPolygonData.getMyPoints().forEach(c -> {
			this.getPoints().add(c.getX()*pane.getWidth());
			this.getPoints().add(c.getY()*pane.getHeight());
		});
	}
}
