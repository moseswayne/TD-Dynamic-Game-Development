package test;

import java.util.ArrayList;

/*import gamedata.composition.MoveWithSetPathData;
import gameengine.actors.properties.LimitedHealthProperty;
import gameengine.actors.properties.MoveWithSetPathProperty;
import gameengine.actors.propertygen.HealthProperty;
import gameengine.actors.propertygen.IActProperty;*/
import gameengine.grid.ActorGrid;
import gameengine.grid.classes.Coordinates;
/*import gameengine.grid.interfaces.ActorGrid.MasterGrid;
import gameengine.grid.interfaces.ActorGrid.ReadAndMoveGrid;
import gameengine.grid.interfaces.ActorGrid.ReadableGrid;*/
import gameengine.grid.interfaces.Identifiers.Grid2D;

public class tester {

	public static void main(String[] args) {
		//MoveWithSetPathData data = new MoveWithSetPathData();
		ArrayList<Grid2D> path = new ArrayList<>();
		Grid2D a = new Coordinates(0,0);
		Grid2D b = new Coordinates(1,1);
		Grid2D c = new Coordinates(2,2);
		path.add(a);
		path.add(b);
		path.add(c);
		//data.addFullPath(path);
		//IActProperty<MasterGrid> prop = new MoveWithSetPathProperty<>(data);
		//HealthProperty health = new LimitedHealthProperty(100.);
		//Troop troop = new Troop(1, health, prop);
		ActorGrid actor = new ActorGrid(10,10, null, null);
		//actor.addEnemy(null, 1, 3, 3);
		actor.step();
	}
}