package gameengine.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gameengine.actors.management.Actor;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.classes.ActorFinder;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.classes.DisplayInfo;
import gameengine.grid.interfaces.ActorGrid.MasterGrid;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import gameengine.grid.interfaces.Identifiers.SettableActorLocator;
import gameengine.grid.interfaces.controllergrid.ControllableGrid;
import gameengine.grid.interfaces.controllerinfo.GridHandler;
import gameengine.grid.interfaces.frontendinfo.FrontEndInformation;
import gamestatus.WriteableGameStatus;
import types.BasicActorType;
import ui.player.listener.ListenQueue;
import util.observerobservable.VoogaObservableMap;
/**
 * 
 * @author Gideon
 * This class is responsible for maintaining the Vooga_Grid.
 * This entails giving actors access to their own and other actors states,
 * allowing for movement, spawning, deleting, filtering, stepping, and more. 
 * This class is designed to be used by all actors in decision making through its
 * implementation of MasterGrid and is also designed to be used by the controller
 * through its implementation of ControllableGrid. MasterGrid extends a series
 * of interfaces with differing levels of access rights which can be passed
 * accordingly to the actor properties that need to use them.
 * 
 */
public class ActorGrid extends VoogaObservableMap<Integer, FrontEndInformation> implements MasterGrid, ControllableGrid{
	
	private Coordinates limits;
	private Collection<SettableActorLocator> actors;
	private Function<Integer, Actor> actorMaker;
	private Stack<SettableActorLocator> newActors;
	private GridHandler myHandler;
	
	public ActorGrid(double maxX, double maxY, GridHandler myHandler, Function<Integer, Actor> actorMaker){
		super();
		limits = new Coordinates(maxX, maxY);
		actors = new ArrayList<>();
		newActors = new Stack<>();
		this.actorMaker = actorMaker;
		this.myHandler = myHandler;
	}

	/**
	 * Steps the grid which includes filtering out all actors
	 * that are no longer valid, spawning actors from the last step
	 * (to avoid concurrent modifications), and updating the observable
	 * map for the display info to be displayed during gameplay.
	 * 
	 * See {@link Steppable}
	 */
	@Override
	public void step() {
		actors.forEach(a -> a.getActor().act(this));
		addNewActors();
		updateActors();
		actors = filter(actors, a -> a.getActor().isActive());
		myMap = Collections.unmodifiableMap(actors.stream()
				.collect(Collectors.toMap(a -> a.getActor().getID(), 
						a -> new DisplayInfo(a.getLocation(), 
								a.getActor()))));
		notifyObservers();
	}
	
	/**
	 * Applies the exit condition to all actors that are no longer
	 * active and resets the actors Collection to only the valid actors
	 */
	private void updateActors(){
		filter(actors, a -> !a.getActor().isActive()).stream().forEach(a -> a.getActor().exit(this));
		actors = filter(actors, a -> a.getActor().isActive());
	}
	
	/**
	 * Adds the new actors to a stack to be added to the actors Collection
	 * during the stepping of the grid (avoids concurrent modification errors)
	 */
	private void addNewActors(){
		while(!newActors.isEmpty()){
			actors.add(newActors.pop());
		}
	}
	
	/**
	 * @param items the collection that will be filtered
	 * @param predicate the predicate to base the filtering on
	 * @return a collection of items filtered based on the predicate
	 */
	private <T> Collection<T> filter(Collection<T> items, Predicate<T> predicate){
		return items.stream()
				.filter(t -> predicate.test(t))
				.collect(Collectors.toList());
	}
	
	/**
	 * @param items the collection that will be mapped
	 * @param function the function to map the input to the output
	 * @return A Collection of items mapped to their output based on the function passed
	 */
	private <I,O> Collection<O> map(Collection<I> items, Function<I,O> function){
		return items.stream()
				.map(i -> function.apply(i))
				.collect(Collectors.toList());
	}
	
	/**
	 * @param x1 x loc of coordinate 1
	 * @param x2 x loc of coordinate 2
	 * @param y1 y loc of coordinate 1
	 * @param y2 y loc of coordinate 2
	 * @return the distance between coordinate 1 and coordinate 2
	 */
	private double distance(double x1, double x2, double y1, double y2){
		double xDifSquared = Math.pow(x2-x1, 2);
		double yDifSquared = Math.pow(y2-y1, 2);
		return Math.pow(xDifSquared + yDifSquared, 0.5);
	}
	
	/**
	 * @param type the actor type to find instances of
	 * @return a Collection of all actors currently active which are of type specified by type parameter
	 */
	private Collection<SettableActorLocator> specificActorTypes(BasicActorType type){
		return filter(actors, a -> a.getActor().getType().equals(type));
	}
	
	/**
	 * @param ID the Actor trying to be found on the grid
	 * @return the SettableActorLocator which contains the actor with the specified ID and its location
	 */
	private SettableActorLocator getActorFromID(int ID){
		Collection<SettableActorLocator> foundIDs = filter(actors, a-> a.getActor().getID() == ID);
		if(foundIDs.size() != 1) 
			throw new IllegalStateException("found an invalid number of id's ~ lines 84 ActorGrid");
		return foundIDs.iterator().next();
	}

	/**
	 * Moves the actor with the specified ID to the given location
	 * See {@link ReadAndMoveGrid}
	 */
	@Override
	public void move(int ID, double newX, double newY) {
		SettableActorLocator actor = getActorFromID(ID);
		actor.setLocation(newX, newY);
	}
	
	/**
	 * Returns all SettableActorLocators of specified type
	 * that are within a specific radius (based on x y and radius passed)
	 */
	private Collection<SettableActorLocator> getActorsInRadius(double x, double y, double radius, BasicActorType type){
		Collection<SettableActorLocator> filteredTypes = specificActorTypes(type);
		return filter(filteredTypes, a -> distance(a.getLocation().getX(), x, a.getLocation().getY(), y) <= radius);
	}

	/**
	 * Returns all locations of actors within a specific radius of the specified type
	 * 
	 * See {@link ReadableGrid}
	 */
	@Override
	public Collection<Grid2D> getActorLocationsInRadius(double x, double y, double radius, BasicActorType type) {
		Collection<SettableActorLocator> actorsinRadius = getActorsInRadius(x, y, radius, type);
		return Collections.unmodifiableCollection(map(actorsinRadius, a -> a.getLocation()));
	}
	
	/**
	 * Returns all damagable consumers for actors within a specific radius of the specified type
	 * Maps the damagable consumer to the health remaining for that actor
	 * 
	 * See {@link ReadAndDamageGrid}
	 */
	@Override
	public Map<Consumer<Double>, Double> getActorDamagablesInRadius(double x,
			double y, double radius, BasicActorType type) {
		return Collections.unmodifiableMap(getActorsInRadius(x, y, radius, type).stream()
					.collect(Collectors.toMap(
							e -> e.getActor().applyDamage(), e -> e.getActor().getRemainingHealth())));
	}

	/**
	 * Returns the location of a given actor with the specified ID
	 * 
	 * See {@link ReadableGrid}
	 */
	@Override
	public Grid2D getLocationOf(int ID) {
		return getActorFromID(ID).getLocation();
	}

	/**
	 * Returns all locations for actors of the specified type 
	 * 
	 * See {@link ReadableGrid}
	 */
	@Override
	public Collection<Grid2D> getActorLocations(BasicActorType type) {
		return Collections.unmodifiableCollection(map(specificActorTypes(type), a -> a.getLocation()));
	}

	/**
	 * Returns whether or not a location is valid given the specifiecations
	 * of the grid
	 * 
	 * See {@link ReadableGrid}
	 */
	@Override
	public boolean isValidLoc(double x, double y) {
		return x >= 0 && y >= 0 && x <= getMaxX() && y <= getMaxY();
	}

	/**
	 * Returns the maximum X coordinate for the grid
	 * 
	 * See {@link ReadableGrid}
	 */
	@Override
	public double getMaxX() {
		return limits.getY();
	}

	/**
	 * Returns the maximum Y coordinate for the grid
	 * 
	 * See {@link ReadableGrid}
	 */
	@Override
	public double getMaxY() {
		return limits.getY();
	}
	
	/**
	 * Removes the Actor with the specified ID from the Grid if
	 * that actor is currently active
	 * 
	 * See {@link ControllableGrid}
	 */
	@Override
	public void removeActor(int ID) {
		actors = filter(actors, a -> a.getActor().getID() != ID);
	}
	
	/**
	 * Allows for the controller to add the actor to the Grid
	 * with the specified X and Y location
	 * 
	 * See {@link ControllableGrid}
	 */
	@Override
	public void controllerSpawnActor(Actor actor, double startX, double startY){
		actors.add(new ActorFinder(new Coordinates(startX, startY), actor));
	}
	
	/**
	 * @param newActor The Actor which will be added to the list of active actors
	 * @param startX the starting x location for the actor
	 * @param startY the starting y location for the actor
	 */
	private void addActor(Actor newActor, double startX, double startY){
		SettableActorLocator movingActor = new ActorFinder(new Coordinates(startX, startY), newActor);
		newActors.push(movingActor);
	}

	/**
	 * Gives the actors access to spawning a new actor in the grid.
	 * The actors is generated using a factory function passed
	 * in to the constructor the the class
	 * 
	 * See {@link ReadAndSpawnGrid}
	 */
	@Override
	public void actorSpawnActor(Integer actorType, double startX, double startY, Consumer<Collection<IActProperty<MasterGrid>>> action) {
		Actor newActor = actorMaker.apply(actorType);
		addActor(newActor, startX, startY);
		newActor.addProperty(action);
	}

	/**
	 * Returns the damagable for an actor based on the ID passed in
	 * 
	 * See {@link ReadAndDamageGrid}
	 */
	@Override
	public Consumer<Double> getMyDamageable(int actorID) {
		return getActorFromID(actorID).getActor().applyDamage();
	}

	/**
	 * Returns the WritableGameStatus so that
	 * actors can edit the status of the game according to the
	 * way that they are damaged, killed, and so on
	 * 
	 * See {@link ReadAndDamageGrid}
	 */
	@Override
	public WriteableGameStatus getWriteableGameStatus() {
		return myHandler.getWriteableGameStatus();
	}

	/**
	 * Returns the event Queue which contains the keys pressed 
	 * so that actors can act based on these keys
	 * 
	 * See {@link ReadableGrid}
	 */
	@Override
	public ListenQueue getEventQueue() {
		return myHandler.getEventQueue();
	}

}
