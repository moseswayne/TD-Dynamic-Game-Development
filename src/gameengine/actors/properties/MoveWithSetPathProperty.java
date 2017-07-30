package gameengine.actors.properties;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import gamedata.composition.MoveWithSetPathData;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.ActorGrid.ReadAndMoveGrid;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import util.PathUtil;
/**
 * this property needs the path as list of coordinates. The pathCoordinates list comes 
 * from PathFinder.getPathCoordinates(Integer enemy Path Index,  double increment)  
 * turn that list into a queue, and poll a coordinate to move to at every
 * step. 
 * 
 * @author Anh
 * @author Maddie
 *
 */
public class MoveWithSetPathProperty<G extends ReadAndMoveGrid> implements IActProperty<G>{
	private Queue<Grid2D> myPathCoordinates;
	 
	
	public MoveWithSetPathProperty(MoveWithSetPathData data){
		//Apply random path to current actor
		myPathCoordinates = new LinkedList<>(getRandomSteps(data.getMyPaths(), data.getMySpeed()));
	}
	
	/**
	 * Get a random path of the available options, taking
	 * into account how speed will affect step size.
	 * 
	 * @param paths list of assigned paths 
	 * @return Random path, accommodating speed
	 */
	
	public List<Grid2D> getRandomSteps(List<List<Grid2D>> paths, double speed){

		if (paths.size() == 0){
			List<Grid2D> path = new ArrayList<Grid2D>();
			path.add(new Coordinates(0,0));
			path.add(new Coordinates(0.5,0.5));
			path.add(new Coordinates(1,1));
			paths.add(path);
		}
	
		int rand = (new Random()).nextInt(paths.size());
			 
	
		return PathUtil.getPathCoordinates(paths.get(rand), speed);
	}
	
	@Override
	public void action(G grid, Integer actorID) {
		// TODO Auto-generated method stub
		if (!myPathCoordinates.isEmpty()){
			// poll a coordinate from myPathCoordinates to set the enemy location to
			Grid2D newLoc = myPathCoordinates.poll();
			grid.move(actorID, newLoc.getX(), newLoc.getY()); 
		}
	}

	@Override
	public boolean isOn() {
		// TODO Auto-generated method stub
		return true;
	}

}
