package gameengine.actors.status;

/**
 * Helper utility class that determines the viability of a certain duration,
 * usually for statuses. Returns a boolean delineating whether a given action
 * has passed its duration
 * 
 * @author Moses Wayne
 *
 */
public class StatusDuration {

	private Integer myDuration;
	private Integer lifeLength;

	public StatusDuration(int dur) {
		myDuration = dur;
		lifeLength = 0;
	}

	public boolean stillOn() {
		return (lifeLength++) < myDuration;
	}

	public Integer getLifeLength() {
		return lifeLength;
	}
}
