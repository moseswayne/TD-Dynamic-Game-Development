package gameengine.actors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import gameengine.actors.management.Actor;
import gameengine.actors.propertygen.HealthProperty;
import gameengine.actors.propertygen.IActProperty;
import gameengine.grid.interfaces.ActorGrid.MasterGrid;
import types.BasicActorType;

/**
 * Class implementing the Actor interface utilizing the composition design
 * pattern to allow for flexible use of this class. Virtually the same as the
 * interface it implements, except with actual implementation of methods.
 * 
 * @author Moses Wayne
 *
 */
public class MainActor implements Actor {

	private Integer myID;
	private Integer myOption;
	private HealthProperty myHealth;
	private BasicActorType myType;
	private List<IActProperty<MasterGrid>> myProperties;
	private List<IActProperty<MasterGrid>> myExits;

	public MainActor(BasicActorType type, Integer option, Integer id, HealthProperty health) {
		myType = type;
		myID = id;
		myHealth = health;
		myProperties = new ArrayList<>();
		myExits = new ArrayList<>();
		myOption = option;
	}

	@SuppressWarnings("unchecked")
	public MainActor(BasicActorType type, Integer option, Integer id, HealthProperty health,
			IActProperty<MasterGrid>... properties) {
		this(type, option, id, health);
		for (IActProperty<MasterGrid> p : properties) {
			myProperties.add(p);
		}
	}

	/**
	 * Calls the action method for each of the IActProperty components of the
	 * actor that are "On"
	 * 
	 * @see Actor#act(MasterGrid)
	 */
	@Override
	public void act(MasterGrid grid) {
		myProperties.stream().filter(prop -> prop.isOn()).forEach(prop -> prop.action(grid, myID));
	}

	/**
	 * Returns whether the health property indicates the actor is alive
	 * 
	 * @see Actor#isActive()
	 */
	@Override
	public boolean isActive() {
		return myHealth.isAlive();
	}

	/**
	 * Returns a consumer that allows manipulation of the health property
	 * 
	 * @see Actor#applyDamage()
	 */
	@Override
	public Consumer<Double> applyDamage() {
		return (damage) -> {
			myHealth.apply(damage);
		};
	}

	/**
	 * Returns unique identifier of this MainActor
	 * 
	 * @see Actor#getID()
	 */
	@Override
	public Integer getID() {
		return myID;
	}

	/**
	 * Returns consumer that allows for the manipulation of the properties held
	 * by this actor
	 * 
	 * @see Actor#addProperty(Consumer)
	 */
	@Override
	public void addProperty(Consumer<Collection<IActProperty<MasterGrid>>> function) {
		function.accept(myProperties);
	}

	/**
	 * Returns the BasicActorType of this actor
	 * 
	 * @see Actor#getType()
	 */
	@Override
	public BasicActorType getType() {
		return myType;
	}

	/**
	 * Returns the "indexed" number of this MainActor delineating what was used
	 * to create it
	 * 
	 * @see Actor#getMyOption()
	 */
	@Override
	public Integer getMyOption() {
		return myOption;
	}

	/**
	 * Returns percent health as specified by the health property
	 * 
	 * @see Actor#getPercentHealth()
	 */
	@Override
	public double getPercentHealth() {
		return myHealth.getPercent();
	}

	/**
	 * Returns double representing remaining health as specified by the health
	 * property
	 * 
	 * @see Actor#getRemainingHealth()
	 */
	@Override
	public double getRemainingHealth() {
		return myHealth.getRemaining();
	}

	/**
	 * Method called when MainActor is removed, runs all "exit properties"
	 * regardless of "On" status
	 * 
	 * @see Actor#exit(MasterGrid)
	 */
	@Override
	public void exit(MasterGrid grid) {
		myExits.stream().forEach(prop -> prop.action(grid, myID));
	}

	/**
	 * Returns consumer to alter the collection of exit properties
	 * 
	 * @see Actor#changeExit(Consumer)
	 */
	@Override
	public void changeExit(Consumer<Collection<IActProperty<MasterGrid>>> action) {
		action.accept(myExits);
	}

}
