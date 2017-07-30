package ui.authoring.map.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gamedata.ActorData;
import gamedata.BasePlacementData;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import util.Location;
import util.Tuple;

public class BaseLayerView extends Layer {

	private boolean DID_LAUNCH;
	private boolean isActive = false;
	private BasePlacementData myData;
	private List<UIBase> myBases;
	private Optional<UIBase> myCurrentBase = Optional.ofNullable(null);

	private EventHandler<MouseEvent> myEvent = e -> {
		
		// base is being dragged
		if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			myCurrentBase.ifPresent(base -> dragBase(base, new Location(e.getX(), e.getY())));
		}

		// base is released from drag
		else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			System.out.println("Mouse release detected...");
			myCurrentBase.ifPresent(base -> updateBaseLocation(base, new Location(e.getX(), e.getY())));
			myCurrentBase = Optional.ofNullable(null);
		}

	};
	private EventHandler<MouseEvent> myBaseEvent = e -> {

		if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			System.out.println("MOUSE_PRESSED...");
			myCurrentBase = Optional.ofNullable((UIBase) e.getSource());
		}

		// double click
		if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED) && e.getClickCount() == 2) {
			System.out.println("double click detected...");
			Optional.ofNullable((UIBase) e.getSource()).ifPresent(base -> addBaseToLoc(base.getData().x,
					base.getData().y));
		}

	};

	private void dragBase(UIBase base, Location location) {
		base.updateLocationUI(location);
	}

	/**
	 * 
	 * @param base the UIBase contains the actor data and location
	 * @param location The new location that UIBase will contain
	 */
	private void updateBaseLocation(UIBase base, Location location) {
		System.out.println("Updating Base Location to " + location);
		Grid2D p = compressLocation(location);
		base.updateLocationData(p.getX(), p.getY());
		base.updateLayout(this);
		this.myData.getMyActorToLocation().forEach(data -> {
			System.out.println(data);
		});
	}

	public BaseLayerView(BasePlacementData data) {
		myData = data;
		myBases = new ArrayList<>();
		this.addEventHandler(MouseEvent.ANY, myEvent);
		widthProperty().addListener(e -> sizeDidChange());
		heightProperty().addListener(e -> sizeDidChange());
	}
	


	@Override
	protected void layoutChildren() {
		if (!DID_LAUNCH)
			loadBaseData();
		DID_LAUNCH = true;
	}
	
	private void sizeDidChange() {
		this.myBases.forEach(path -> path.updateLayout(this));
	}

	private void loadBaseData() {
		System.out.println("");
		myData.getMyActorToLocation().forEach((t) -> {
			addBaseUI(t.x, t.y);
			System.out.println("Grid Location: "+t.y);
		});
	}

	/**
	 * purpose is to translate the scene coordinates to local coordinates
	 * @param data
	 * @param sceneLoc
	 */
	public void addBase(ActorData data, Location sceneLoc) {
		addBaseToLoc(data, compressLocation(sceneToLocal(sceneLoc)));
	}
	
	/**
	 * adds base to data 
	 * @param data
	 * @param location
	 */
	private void addBaseToLoc(ActorData data, Coordinates location) {
		System.out.println("local coordinates " + location);
		this.myData.addBase(new Tuple<>(data, location));
		UIBase b = addBaseUI(data, location);
		this.myCurrentBase = Optional.of(b);
	}

	private Location sceneToLocal(Location loc) {
		double sceneH = this.getScene().getHeight();
		double sceneW = this.getScene().getWidth();
		double vInset = (sceneH - this.getHeight()) / 2.0;
		double hInset = (sceneW - this.getWidth()) / 2.0;
		return new Location(loc.getX() - hInset, loc.getY() - vInset);
	}

	private UIBase addBaseUI(ActorData data, Coordinates loc) {
		System.out.println("\rUIBASE: "+loc);
		UIBase base = new UIBase(data, loc, this);
		base.addEventHandler(MouseEvent.ANY, myBaseEvent);
		this.getChildren().add(base);
		return base;
	}
	
	
//	private Location decompressCoordinates(Grid2D c){
//		return new Location(c.getX()* this.widthProperty().get(), c.getY()*this.heightProperty().get());
//	}

	private Coordinates compressLocation(Location e) {
		Coordinates c = new Coordinates(e.getX() / this.getWidth(), e.getY() / this.getHeight());
		return c;
	}

	private void deleteBase(UIBase base) {
		System.out.println("Removing base " + base.getData().x.getName());
		this.myBases.remove(base);
		this.myData.removeBase(base.getData());
	}

	@Override
	public void activate() {
		isActive = true;
	}

	@Override
	public void deactivate() {
		isActive = false;
	}

	@Override
	public void clear() {
		myBases.forEach(base -> deleteBase(base));

	}

	@Override
	public void undo() {
		if (myBases.isEmpty())
			return;
		deleteBase(myBases.get(myBases.size() - 1));
	}

	@Override
	public void setColor(Color c) {
		// do nothing
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

}
