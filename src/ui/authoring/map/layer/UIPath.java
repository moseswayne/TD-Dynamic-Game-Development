package ui.authoring.map.layer;

import java.util.ArrayList;
import java.util.List;

import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import ui.general.CustomColors;
import util.Location;
import util.VoogaException;

public class UIPath {
	
	private static final double LINE_WIDTH = 2.0;
	
	private List<UIPoint> myPoints = new ArrayList<>();
	private List<Line> myLines = new ArrayList<>();
	
	public UIPath(){
		
	}
	
	
	/**
	 * Adds a list of points onto the pane that is given.
	 * Also takes care of making lines in between them
	 * 
	 * @param path the list of points to add onto the screen
	 * @param pane the pane that the points are going to be added to
	 */
	public UIPath(List<Grid2D> points, Pane pane){
		points.forEach(p -> {
			addPointTo(
					new UIPoint(p, pane.getWidth()*p.getX(), pane.getHeight()*p.getY()),
					pane);
		});
		
		//dependency
		pane.getChildren().remove(myLines.remove(myLines.size() - 1));
	}
	
	
	private Line addPointTo(UIPoint point, Pane pane){
		
		pane.getChildren().add(point);
		myPoints.add(point);
		
		Line line = new Line(point.getCenterX(), point.getCenterY(),
				 point.getCenterX(), point.getCenterY());
		line.setStrokeWidth(LINE_WIDTH);
		line.setFill(CustomColors.BLACK_GRAY);

		pane.getChildren().add(line);
		myLines.add(line);
		
		line.startXProperty().bind(pane.widthProperty().multiply(point.getCoordinates().getX()));
		line.startYProperty().bind(pane.heightProperty().multiply(point.getCoordinates().getY()));
		
		try{
			Line previousLine = myLines.get(myLines.size() - 2);

			previousLine.endXProperty().bind(pane.widthProperty().multiply(point.getCoordinates().getX()));
			previousLine.endYProperty().bind(pane.heightProperty().multiply(point.getCoordinates().getY()));
		}catch (ArrayIndexOutOfBoundsException e){
		}
		return line;
	}
	
	public void addPointAndMouseDraggableLine(UIPoint point, Pane pane){
		Line line = addPointTo(point, pane);
		pane.setOnMouseMoved(event -> {
			line.setEndX(event.getX());
			line.setEndY(event.getY());
		});
		pane.setOnMouseDragged(event -> {
			line.setEndX(event.getX());
			line.setEndY(event.getY());
		});
		
	}
	
	public List<UIPoint> getPoints(){
		return myPoints;
	}
	
	public List<Line> getLines(){
		return myLines;
	}
	

	public void reload(Pane pane) {
		this.myPoints.forEach(point -> {
			point.setCenterX(point.getCoordinates().getX()*pane.getWidth());
			point.setCenterY(point.getCoordinates().getY()*pane.getHeight());
		});
	}
	
	public void finishPath(Pane pane){
		pane.getChildren().remove(myLines.remove(myLines.size() - 1));
	}
	
	@Override
	public String toString(){
		return this.myPoints.toString();
	}
	
	
	
}
