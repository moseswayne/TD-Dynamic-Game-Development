package gameengine.grid.testers.ControllerTesters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gameengine.actors.management.Actor;
import gameengine.grid.ActorGrid;
import gameengine.grid.interfaces.controllergrid.ControllableGrid;
import gameengine.grid.testers.TestActor;

public class ControllableGridTester {
	
	private ControllableGrid myGrid = null;
	private Actor myActor = null;

	
    public void setUp () {
       myGrid = new ActorGrid(1, 1, null, i -> new TestActor(-5));
       myActor = new TestActor(10);
       myGrid.controllerSpawnActor(myActor, 1, 5);
    }
    
    // tests the locational ability of finding actors on the grid
	@Test
	public void locationTest() {
		setUp();
		assertEquals(myGrid.getLocationOf(myActor.getID()).getX(), 1, 0);
		assertEquals(myGrid.getLocationOf(myActor.getID()).getY(), 5, 0);
	}
	
	// tests stepping the grid
	@Test
	public void stepTest() {
		setUp();
		myGrid.step();
		assertEquals(myGrid.getLocationOf(myActor.getID()).getX(), 3, 0);
		assertEquals(myGrid.getLocationOf(myActor.getID()).getY(), 0, 0);
		myGrid.step();
		assertEquals(myGrid.getLocationOf(myActor.getID()).getX(), 5, 0);
		assertEquals(myGrid.getLocationOf(myActor.getID()).getY(), -5, 0);
	}
	
	// tests moving an actor
	@Test
	public void moveTest() {
		setUp();
		myGrid.move(myActor.getID(), 12, -5);
		assertEquals(myGrid.getLocationOf(myActor.getID()).getX(), 12, 0);
		assertEquals(myGrid.getLocationOf(myActor.getID()).getY(), -5, 0);
	}
	
	// Testing to make sure that an illegal state exception is
	// thrown when an ID not in grid is accessed
	@Test(expected = IllegalStateException.class) 
	public void removeAndAccessTest() {
		setUp();
		myGrid.removeActor(myActor.getID());
		myGrid.getLocationOf(myActor.getID());
	}

}
