package gamedata.composition;


import java.util.ArrayList;

import java.util.List;

import gamedata.compositiongen.MoveData;
import gameengine.grid.interfaces.Identifiers.Grid2D;

/**
 * Gives the actor a set path to move upon.
 * 
 * @author maddie
 * @author Anh
 *
 */
public class MoveWithSetPathData extends MoveData {

	List<List<Grid2D>>myPaths;
	
	public MoveWithSetPathData(){
		this(new ArrayList<List<Grid2D>>(), 0.0);
	}
	
	/**
	 * this data object gets a list of assigned paths, pick a random one and call PathFinder to calculate the full 
	 * path with regards to speed. 
	 * 
	 * @param myAssignedPaths  get this from PathData.getAssignedPaths the list of path options without taking into
	 * account the speed
	 * @param speed
	 */
	
	public MoveWithSetPathData(List<List<Grid2D>> myAssignedPaths, Double speed){
		super(speed);
		myPaths = myAssignedPaths;
	}

	public List<List<Grid2D>> getMyPaths() {
		return myPaths;
	}

	public void setMyPaths(List<List<Grid2D>> myPaths) {
		this.myPaths = myPaths;
	}

	
}
