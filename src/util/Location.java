package util;

import gameengine.grid.interfaces.Identifiers.Grid2D;

public class Location implements Grid2D{
	private Tuple<Double,Double> loc;
	
	public Location(Double col, Double row){
		setValue(col, row);
	}

	public double getX() {
		return loc.x;
	}
	
	public double getY() {
		return loc.y;
	}

	public void setValue(Double col, Double row) {
		this.loc = new Tuple<Double,Double>(col, row);
	}
	
	public String toString(){
		return getX() + " " + getY();
	}
}
