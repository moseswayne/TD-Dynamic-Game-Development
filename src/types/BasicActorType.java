package types;

/**
 * Refactored from enum list to allow
 * for categories
 * 
 * @author maddiebriere
 *
 */

public class BasicActorType implements Comparable<Object> {
	private String type;
	private String imagePath;
	private boolean placeable;
	
	public BasicActorType(String type){
		this(type, true);
	}
	
	public BasicActorType(String type, boolean placeable){
		this(type, "", placeable);
	}
	
	public BasicActorType(String type, String imagePath){
		this(type, imagePath, true);
	}
	
	public BasicActorType(String type, String imagePath, boolean placeable){
		this.type=type;
		this.imagePath = imagePath;
		this.placeable=placeable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isPlaceable() {
		return placeable;
	}

	public void setPlaceable(boolean placeable) {
		this.placeable = placeable;
	}
	
	public void togglePlaceable(){
		placeable = !placeable;
	}

	@Override
	public boolean equals(Object o){
		if(o==null){
			return false;
		}
		if(!(o instanceof BasicActorType)){
			return false;
		}
		BasicActorType b = (BasicActorType)o;
		return b.getType().equals(this.getType());
	}
	
	public String toString(){
		return this.getType();
	}

	@Override
	public int compareTo(Object o) {
		if(o == null){
			return -1;
		}
		if(!(o instanceof BasicActorType)){
			return -1;
		}
		return this.getType().compareTo(((BasicActorType)o).getType());
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
}
