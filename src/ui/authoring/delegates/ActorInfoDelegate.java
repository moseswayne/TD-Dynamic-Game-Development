package ui.authoring.delegates;

import gamedata.ActorData;
import util.Location;

public interface ActorInfoDelegate {
	public abstract void addActorToBase(ActorData data, Location mouseLoc);
}
