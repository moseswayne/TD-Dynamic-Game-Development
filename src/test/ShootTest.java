package test;

/*import gamedata.composition.LimitedHealthData;
import gamedata.compositiongen.ShootData;
import gameengine.actors.MainActor;
import gameengine.actors.management.Actor;
import gameengine.actors.properties.LimitedHealthProperty;
import gameengine.actors.properties.ShootTargetFarProperty;
import gameengine.actors.properties.ShootTargetNearProperty;
import gameengine.actors.propertygen.ShootTargetProperty;
import gameengine.grid.ActorGrid;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.ActorGrid.MasterGrid;
import types.BasicActorType;*/

public class ShootTest {

	public static void main(String[] args) {
	/*	ActorGrid grid = new ActorGrid(10,10, null, null);
		LimitedHealthData data = new LimitedHealthData(100.);
		/*Actor actor = new MainActor(BasicActorType.Troop, 1, new LimitedHealthProperty(data));
		Actor actor2 = new MainActor(BasicActorType.Troop, 2, new LimitedHealthProperty(data));
		grid.spawn(actor, 3, 3);
		grid.spawn(actor2, 2.5, 2.5);
		MasterGrid myGrid = grid;
		ShootData shootdata = new ShootData(10, 10, BasicActorType.Troop);
		ShootTargetProperty<MasterGrid> prop = new ShootTargetFarProperty<MasterGrid>(shootdata);*/
		//prop.getEnemyToShoot(myGrid.getActorLocationsInRadius(1, 1, 10, BasicActorType.Troop), new Coordinates(1,1)).stream().forEach(point -> System.out.println(point.getX()+", "+point.getY()));
	}
}
