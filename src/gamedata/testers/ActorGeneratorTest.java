package gamedata.testers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import builders.objectgen.ActorGenerator;
import builders.objectgen.PathGenerator;
import gamedata.ActorData;
import gamedata.BasicData;
import gamedata.composition.BaseDamageData;
import gamedata.composition.MoveWithSetPathData;
import gamedata.composition.ShootTargetFarData;
import gamedata.compositiongen.Data;
import gamedata.compositiongen.HealthData;
import gameengine.actors.MainActor;
import gameengine.actors.management.Actor;
import gameengine.actors.propertygen.HealthProperty;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.ActorGrid.MasterGrid;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import types.BasicActorType;

/**
 * Tester for Actor Generator
 * 
 * @author maddiebriere
 *
 */

public class ActorGeneratorTest {

	@Test
	public void basicGenerate() {
		System.out.println("\n"
				+ "Generate 1");
		ActorData toTest = new ActorData(new BasicActorType("Tower"), new BasicData("Bob", "imagePath"));
		MainActor actor = ActorGenerator.makeActor(1, toTest);
		assertNotEquals(actor, null);
		actor.getID();
		int option = actor.getMyOption();
		BasicActorType type = actor.getType();
		assertEquals(type, new BasicActorType("Tower"));
		//assertEquals(ID, 0);//first item generated, should yield a 0
		assertEquals(option, 1);
		System.out.println(actor.getMyProperties().size());
	}
	
	@Test
	public void noErrors(){
		
		System.out.println("\nGenerate 2");
		ActorData toTest2 = new ActorData(new BasicActorType("Troop"), new BasicData("Bob", "imagePath"));
		toTest2.addData(new ShootTargetFarData(100.0, 50, new BasicActorType("Base"), 10, 10.0));
		MainActor actor1 = ActorGenerator.makeActor(5, toTest2);
		System.out.println(actor1.getMyProperties().size());
		
		System.out.println("\nGenerate 3");
		ActorData toTest3 = new ActorData(new BasicActorType("Troop"), new BasicData("Billy", "imagePath"));
		
		List<Grid2D> samplePath = new ArrayList<Grid2D>();
		samplePath.add(new Coordinates(0,0.5));
		samplePath.add(new Coordinates(0.25, 0.5));
		samplePath.add(new Coordinates(0.5, 0.5));
		samplePath.add(new Coordinates(0.75, 0.5));
		samplePath.add(new Coordinates(1.0, 0.5));
		
		List<List<Grid2D>> paths = new ArrayList<List<Grid2D>>();
		paths.add(samplePath);
		
		MoveWithSetPathData m = new MoveWithSetPathData(paths, 1.0);
		toTest3.addData(m);
		
		MainActor actor2 = ActorGenerator.makeActor(2, toTest3);
		System.out.println(actor2.getMyProperties().size());
	}
	
	@Test
	public void testConstructors(){
		Class<?> clzz = null;
		Class<?> clzz2 = null;
		try {
			clzz = Class.forName("gamedata.ActorData");
			clzz2 = Class.forName("gameengine.actors.MainActor");
		} catch (ClassNotFoundException e) {
			System.out.println("Class fail");
		}
		
		try {
			clzz.getDeclaredConstructor(BasicActorType.class, BasicData.class, HealthData.class, Data[].class);
			clzz2.getDeclaredConstructor(BasicActorType.class, Integer.class, 
			Integer.class, HealthProperty.class, IActProperty[].class);
			System.out.println("NICE");
		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("You fucked up");
		}
		
	}

}
