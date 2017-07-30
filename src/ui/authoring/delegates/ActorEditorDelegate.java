package ui.authoring.delegates;

import gamedata.ActorData;
import util.Location;

public interface ActorEditorDelegate extends PopViewDelegate {
	public abstract void addActorToBase(ActorData data, Location mouseLoc);
}
