package gamedata;

/**
 * Information about the field in a data class. 
 * Knows name and type for use in authoring environment. 
 * Example: 
 * Name = speed
 * Type = double
 * 
 * @author maddiebriere
 *
 */

public class FieldData {
	private String myName;
	private Class<?> myType;
	private Class<?> myChildType;
	
	public FieldData(String myName, Class<?> myType) {
		this(myName, myType, null);
	}
	
	public FieldData(String myName, Class<?> myType, Class<?> myChildType) {
		super();
		this.myName = myName;
		this.myType = myType;
		this.myChildType = myChildType;
	}
	
	
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	public Class<?> getMyType() {
		return myType;
	}
	public void setMyType(Class<?> myType) {
		this.myType = myType;
	}
	public String toString(){
		return String.format("%s   %s", myName, myType);
	}
	public Class<?> getMyChildType() {
		return myChildType;
	}
	public void setMyChildType(Class<?> myChildType) {
		this.myChildType = myChildType;
	}
	
	
}
