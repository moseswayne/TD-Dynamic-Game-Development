package gameengine.grid.testers.actorgridtesters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gameengine.grid.ActorGrid;
import gameengine.grid.interfaces.ActorGrid.ReadableGrid;
import gameengine.grid.testers.TestActor;
import types.BasicActorType;

public class ReadableGridTest {
	
	private ReadableGrid myGrid = null;
	private final int actorID = 3;
	
    public void setUp () {
       ActorGrid grid = new ActorGrid(1, 1, null, i -> new TestActor(i));
       grid.controllerSpawnActor(new TestActor(actorID), 5, 20);
       myGrid = grid;
    }
    
    // tests the locational ability of finding actors on the myGrid
	@Test
	public void locationTest() {
		setUp();
		assertEquals(myGrid.getLocationOf(actorID).getX(), 5, 0);
		assertEquals(myGrid.getLocationOf(actorID).getY(), 20, 0);
	}
	
    // tests the getActorLocations
	@Test
	public void actorTypeFilteringTest() {
		setUp();
		assertEquals(myGrid.getActorLocations(new BasicActorType("troop")).size(), 1);
		assertEquals(myGrid.getActorLocations(new BasicActorType("tower")).size(), 0);
		assertEquals(myGrid.getActorLocations(new BasicActorType("troop")).iterator().next().getX(), 5, 0);
		assertEquals(myGrid.getActorLocations(new BasicActorType("troop")).iterator().next().getY(), 20, 0);
	}
	
    // tests the getActorLocationsInRadius
	@Test
	public void radiusTest() {
		setUp();
		assertEquals(myGrid.getActorLocationsInRadius(5, 25, 5, new BasicActorType("troop")).size(), 1);
		assertEquals(myGrid.getActorLocationsInRadius(5, 25, 4.999, new BasicActorType("troop")).size(), 0);
		assertEquals(myGrid.getActorLocationsInRadius(5, 20, 100, new BasicActorType("tower")).size(), 0);
	}
	
    // tests the boundary system
	@Test
	public void boundaryTest() {
		setUp();
		assertEquals(myGrid.isValidLoc(-1, -1), false);
		assertEquals(myGrid.isValidLoc(1.5, 0), false);
		assertEquals(myGrid.isValidLoc(0, 0), true);
		assertEquals(myGrid.isValidLoc(1, 1), true);
		assertEquals(myGrid.isValidLoc(1, 0), true);
		assertEquals(myGrid.isValidLoc(0, 1), true);
	}

}
