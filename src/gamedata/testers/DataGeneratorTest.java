package gamedata.testers;

import static org.junit.Assert.*;

import org.junit.Test;

import builders.objectgen.DataGenerator;
import gamedata.composition.LimitedHealthData;
import gamedata.compositiongen.Data;
import types.BasicActorType;

/**
 * Tester for DataGenerator
 * 
 * @author maddiebriere
 *
 */

public class DataGeneratorTest {

	@Test
	public void noErrors() {
		assertNotEquals(DataGenerator.makeData("LimitedHealthData"), null);
		System.out.println(DataGenerator.makeData("LimitedHealthData"));
		assertNotEquals(DataGenerator.makeData("MoveWithSetPathData"), null);
		//assertNotEquals(DataGenerator.makeData("MoneyUpgradeData"), null);
		DataGenerator.makeData("LimitedHealthData", 10.0);
		DataGenerator.makeData("ShootTargetNearData", 10.0, 10, new BasicActorType("Tower"), 10, 10.0);
		DataGenerator.makeData("ShootTargetFarData", 10.0, 10, new BasicActorType("Tower"), 10, 10.0);
		//DataGenerator.makeData("MoneyUpgradeData", 1.0);
		assertNotEquals(DataGenerator.makeData("DamageInRadiusData"), null);
	}
	
	public void testSavedData(){
		Data data = DataGenerator.makeData("LimitedHealthData", 10.0);
		LimitedHealthData health = (LimitedHealthData)data;
		assertEquals(10.0, health.getStartHealth());
		
		/*Data data1 = DataGenerator.makeData("AfflictStatusData");
		AfflictStatusData status = (AfflictStatusData)data1;
		assertNotEquals(status, null);*/
	}

}
