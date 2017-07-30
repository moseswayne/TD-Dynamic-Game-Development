package gamedata;

import util.Location;
/**
 * Information for the frontend, sent 
 * from the backend
 * 
 * @author maddiebriere
 *
 */

public class ActorFrontendData {
	
	private final static double HEALTH = 1.0;
	
	private String imagePath;
	private String name; 
	private Location location;
	private double heading;
	private double health;
	private int ID;
	
	public ActorFrontendData(String imagePath, String name, Location location,
			double heading, int ID) {
		this(imagePath, name, location, heading, HEALTH, ID);
	}
	
	public ActorFrontendData(String imagePath, String name, Location location,
			double heading, double health, int ID) {
		super();
		this.imagePath = imagePath;
		this.name = name;
		this.location = location;
		this.heading = heading;
		this.health = health;
		this.ID = ID;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public double getHeading() {
		return heading;
	}
	public void setHeading(double heading) {
		this.heading = heading;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
	
	
}