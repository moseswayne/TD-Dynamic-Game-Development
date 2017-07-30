package ui.authoring.map.layer;

import java.util.ArrayList;
import java.util.List;

import gamedata.LayerData;
import gamedata.MapLayersData;
import gamedata.map.PolygonData;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import ui.general.CustomColors;
import ui.general.ImageViewPane;
import util.Location;
import util.Tuple;

public class PolygonLayerView extends Layer {
	
	private EventHandler<MouseEvent> myMouseEvents = e -> {
		if(e.getButton().equals(MouseButton.SECONDARY)){
			mouseSecondary(e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED)){
			mousePressed(e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED)){
			mouseReleased(e);
		}
		else if(e.getEventType().equals(MouseEvent.MOUSE_MOVED)){
			mouseMoved(e);
		} 
	};
	
	private LayerData myLayerData;
	private UIPolygon myCurrentPolygon;
	private List<UIPolygon> myUIPolygons;
	private Color myColor = CustomColors.AMBER;
	private boolean isActive = false;
	
	public PolygonLayerView(LayerData layerData){
		super();
		myLayerData = layerData;
		myUIPolygons = new ArrayList<>();
		setupPolygonViews();
		widthProperty().addListener(e -> sizeDidChange());
		heightProperty().addListener(e -> sizeDidChange());
	}


	private void setupPolygonViews() {
//		for( PolygonData data : myLayerData.getMyPolygons()){
//			addPolygonFromData(data);
//		}
		loadLayerData(myLayerData);
	}
	
	/**
	 * 
	 * @param data the polygon data tha
	 */
//	private void addPolygonFromData(PolygonData data) {
//		Polygon p = makePolygon(data);
//		p.setFill(myColor);
//		getChildren().add(p);
//	}

	
//	private Polygon makePolygon(PolygonData data){
//		return new Polygon(pointsToArray(
//				decompressedLocations(
//						data.getMyPoints())));
//	}
	
	/**
	 * 
	 * @param locations a list of locations each with xy-values between 0 and 1
	 * @return a copy of the list of locations scaled to the viewing size
	 */
	private List<Grid2D> decompressedLocations(List<Grid2D> locations){
		List<Grid2D> copy = new ArrayList<>();
		locations.forEach(loc -> {
			copy.add(new Location(loc.getX()*this.getWidth(),
					loc.getY()*this.getHeight()));
		});
		return copy;
	}
	
	private double[] pointsToArray(List<Location> myPoints) {
		double[] d = new double[myPoints.size()*2];
		int count = 0;
		for(Location loc : myPoints){
			d[count++] = loc.getX();
			d[count++] = loc.getY();
		}
		return d;
	}

	
	
	/*
	 * MARK: -Mouse Actions 
	 */
	
	private void mousePressed(MouseEvent e) {
		// do nothing
	}
	
	/**
	 * If there isn't a polygon, then add one
	 * else, add a new point to that polygon
	 * @param e
	 */
	private void mouseReleased(MouseEvent e) {
		if (!checkIfPointInBounds(e)) {
	        return;
	    }
		if(myCurrentPolygon == null){
			PolygonData data = new PolygonData(new ArrayList<>());
			this.myLayerData.getMyPolygons().add(data);
			myCurrentPolygon = new UIPolygon(data, this);
			myCurrentPolygon.addPoint(compressPoint(e), this);
			myCurrentPolygon.getPoints().addAll(e.getX(), e.getY());
			setPolygonStyle(myCurrentPolygon);
			getChildren().add(myCurrentPolygon);
			this.myUIPolygons.add(myCurrentPolygon);
		}else{
			myCurrentPolygon.addPoint(compressPoint(e), this);;
		}
	}
	
	private Coordinates compressPoint(MouseEvent e) {
		Coordinates c =   new Coordinates(e.getX()/this.getWidth(), e.getY()/this.getHeight());
		return c;
	}


	private boolean checkIfPointInBounds(MouseEvent e){
		return this.intersects(e.getX(), e.getY(), 1, 1);
	}
	
	private void setPolygonStyle(Polygon polygon) {
		polygon.setFill(myColor);
		polygon.setStroke(myColor.darker());
		polygon.setStrokeWidth(3.0);
	}



	/**
	 * updates the current polygon by removing the last two doubles and adding two more
	 * @param e
	 */
	private void mouseMoved(MouseEvent e) {
		if(myCurrentPolygon!=null){
			int size = myCurrentPolygon.getPoints().size();
			myCurrentPolygon.getPoints().remove(size - 2, size);//TODO i think
			myCurrentPolygon.getPoints().addAll(e.getX(), e.getY());
		}
		
	}
	
	/**
	 * This method is supposed to signify the end of the polygon
	 * making process and finalize it by setting the currentPolygon
	 * to null and adding the polygonData into the layerData.
	 * @param e
	 */
	private void mouseSecondary(MouseEvent e) {
		if(myCurrentPolygon != null){
			myCurrentPolygon = null;
		}
	}
	

	/**
	 * sets the plygons opacity to 100
	 * allows user to add or delete polygons
	 */
	@Override
	public void activate(){
		this.addEventHandler(MouseEvent.ANY, myMouseEvents);
		isActive = true;
	}
	
	/**
	 * dims 
	 */
	@Override
	public void deactivate(){
		this.removeEventHandler(MouseEvent.ANY, myMouseEvents);
		isActive = false;
	}


	@Override
	public void clear() {
		getChildren().clear();
		myLayerData.getMyPolygons().clear();
	}


	@Override
	public void undo() {
		UIPolygon p = this.myUIPolygons.remove(myUIPolygons.size() - 1);
		myLayerData.getMyPolygons().remove(p);
		this.getChildren().remove(p);
	}


	@Override
	public void setColor(Color c) {
		myColor = c;
		
	}


	@Override
	public boolean isActive() {
		return isActive;
	}


	private void sizeDidChange() {		
		this.myUIPolygons.forEach(polygon -> {
			polygon.reload(this);
		});		
	}


	public void loadLayerData(LayerData layerData) {
		layerData.getMyPolygons().forEach(data -> {
			UIPolygon polygon = new UIPolygon(data, this);
			this.setPolygonStyle(polygon);
			this.getChildren().add(polygon);
			this.myUIPolygons.add(polygon);
		});
		
	}

}
