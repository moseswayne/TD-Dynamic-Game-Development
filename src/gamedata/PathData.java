package gamedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameengine.grid.interfaces.Identifiers.Grid2D;
/**
 * Class to represent all Paths created in the authoring 
 * environment, mapped to integers for use in actor creation.
 * 
 * @author maddiebriere
 * @author Ahn
 * @author Talha Koc
 * @author Anh
 *
 */
public class PathData {
	private Map <Integer, List<Grid2D>> myPaths; // the map of all user-defined paths in the game, indexed. 
	private int numOptions;
	
	public PathData(){
		myPaths = new HashMap<Integer, List<Grid2D>>();
		numOptions = 0;
	}
	
	public PathData(Map <Integer, List<Grid2D>> paths){
		myPaths = paths;
		numOptions = paths.size(); 
	}
	
	/**
	 * provide a sub-list of paths given the path indexes assigned for a type of enemy in a particular wave
	 * @param options the list of path indexes for a type of enemy defined in the authoring environment 
	 * @return a list of paths (have not account for speed yet) 
	 */
	public List<List<Grid2D>> getAssignedPaths(List<Integer> options){
		List<List<Grid2D>> myPathList = new ArrayList<List<Grid2D>>(); 
		options.forEach(i -> myPathList.add(myPaths.get(i)));
		return myPathList;
	}
	
	//Add methods for communications with front end
	public List<Grid2D> getPathByIndex(Integer index){
		return myPaths.get(index);
	}
	
	public void addPath(List<Grid2D> newPath){
		myPaths.put(numOptions++, newPath);
	}
	public Map<Integer, List<Grid2D>> getMyPaths() {
		return myPaths;
	}
	public void setMyPaths(Map<Integer, List<Grid2D>> myPaths) {
		this.myPaths = myPaths;
	}
	
	public void clear(){
		myPaths = new HashMap<Integer, List<Grid2D>>();
		numOptions = 0;
	}
	
	public List<Grid2D> poll(){
		if(myPaths.isEmpty()){
			return null;
		}
		return myPaths.get(myPaths.size() - 1);
	}
	
	public List<Grid2D> pop(){
		if(myPaths.isEmpty()){
			return null;
		}
		this.numOptions--;
		return myPaths.remove(myPaths.size() - 1);
	}

	/**
	 * get all base locations in all path, assuming that base is placed at the end of every enemy's path
	 * @return
	 */
	public List<Grid2D> getBaseLocations(){
		List<Grid2D> allBaseLocations = new ArrayList<Grid2D>();
		myPaths.values().forEach(p -> allBaseLocations.add(p.get(p.size()-1)));
		return allBaseLocations;
	}
}

