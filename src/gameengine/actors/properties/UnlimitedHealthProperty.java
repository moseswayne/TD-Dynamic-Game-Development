package gameengine.actors.properties;

import gamedata.composition.UnlimitedHealthData;
import gameengine.actors.propertygen.HealthProperty;

/**
 * Implementing class of the HealthProperty interface that delineates
 * functionality of an unlimited amount of health by virtue of the property
 * being undamageable and always having 100% health
 * 
 * @author Moses Wayne
 *
 */
public class UnlimitedHealthProperty implements HealthProperty {

	public UnlimitedHealthProperty(UnlimitedHealthData unlimited) {

	}

	@Override
	public void apply(double damage) {

	}

	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public double getPercent() {
		return 1;
	}

	@Override
	public double getRemaining() {
		return 0;
	}

}
