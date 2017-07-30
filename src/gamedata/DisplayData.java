package gamedata;

import java.util.List;

/**
 * Display information
 * 
 * @author alexblumenstock
 * @author maddiebriere
 *
 */

public class DisplayData {
	
	private static final String DEFAULT_POSITION= "Left";
	private static final int DEFAULT_WIDTH=100;
	private static final int DEFAULT_HEIGHT=400;
	public static final String DEFAULT_IMAGE="default_map_background_0.jpg";
	private int width;
	private int height;
	private String location;
	private List<ActorData>actorOrder;
	private String backgroundImagePath;
	
	public DisplayData(){
		this(DEFAULT_WIDTH,DEFAULT_HEIGHT,DEFAULT_POSITION,DEFAULT_IMAGE);
	}
	
	public DisplayData(int width, int height, String loc,String backgroundImage) {
		super();
		this.width=width;
		this.height=height;
		this.location=loc;
		this.backgroundImagePath=backgroundImage;
	}
	public void setActorOrder(List<ActorData>list){
		actorOrder=list;
	}
	public List<ActorData> getActorOrder(){
		return actorOrder;
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public void setBackgroundImagePath(String backgroundImagePath) {
		
		this.backgroundImagePath = backgroundImagePath;
	}
	public String toString(){
		return "width:"+this.width+" height:"+this.height+"loc:"+this.location+"order:"+actorOrder.toString();
	}
	
	
}
