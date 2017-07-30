package gamedata.testers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import factories.PropertyFactory;
import gamedata.composition.ActorDamageableData;
import gamedata.composition.BaseDamageData;
import gamedata.composition.LimitedHealthData;
import gamedata.composition.MoveWithSetPathData;
import gamedata.composition.ShootTargetFarData;
import gamedata.composition.ShootTargetNearData;
import gamedata.composition.ShootTargetWithMouseData;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import types.BasicActorType;

/**
 * Test property generation via factory
 * 
 * @author maddiebriere
 *
 */

public class PropertyFactoryTest {

	@Test
	public void test() {
		PropertyFactory p = new PropertyFactory();
		//assertNotEquals(p.make("AfflictStatusProperty", new AfflictStatusData()) ,null);
		assertNotEquals(p.make("BaseDamageProperty", new BaseDamageData(10.0, new BasicActorType("Troop"))), null);
		//assertNotEquals(p.make("DamageableProperty", new DamageableData(10.0, 10.0, BasicActorType.Tower)),null);
		//assertNotEquals(p.make("ImmuneHealthProperty", new ImmuneHealthData()), null);
		assertNotEquals(p.make("LimitedHealthProperty", new LimitedHealthData(10.0)), null);
		assertNotEquals(p.make("MoveWithSetPathProperty", new MoveWithSetPathData(new ArrayList<List<Grid2D>>(), 10.0)), null);
		assertNotEquals(p.make("ShootTargetFarProperty", new ShootTargetFarData(10.0, 10, new BasicActorType("Tower"), 10, 10.0)), null);
		assertNotEquals(p.make("ShootTargetNearProperty", new ShootTargetNearData(10.0, 10, new BasicActorType("Tower"), 10, 10.0)), null);
		assertNotEquals(p.make("ShootTargetWithMouseProperty", new ShootTargetWithMouseData(10.0, 10, new BasicActorType("Tower"), 10, 10.0)), null);
		assertNotEquals(p.make("ActorDamageableProperty", new ActorDamageableData(10.0, new BasicActorType("Tower"))), null);
		//assertNotEquals(p.make("MoveWithProperty", new MoveWithSetPathData(new ArrayList<List<Grid2D>>(), 10.0)), null);
	}

}
