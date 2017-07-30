package gamedata;

import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;

/**
 * 
 * Basic information all actors should have, including
 * a name, imagePath, active status, etc.
 * 
 * @author maddiebriere
 *
 */

public class BasicData {
	
	private static final double HEADING = 0.0;
	
	private String name;
	private boolean isActive;
	private String imagePath;
	private double heading; //angle
	private Grid2D imageDimensions;
	
	public BasicData(String name, String imagePath){
		this.name=name;
		this.imagePath = imagePath;
		this.setImageDimensions(new Coordinates(0.1,0.1));
		heading = HEADING;
		isActive = true;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Grid2D getImageDimensions() {
		return imageDimensions;
	}

	public void setImageDimensions(Grid2D imageDimensions) {
		this.imageDimensions = imageDimensions;
	}

	
	
}
