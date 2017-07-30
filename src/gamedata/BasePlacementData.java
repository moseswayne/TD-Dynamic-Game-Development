package gamedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import util.Tuple;

public class BasePlacementData {
	
	private List<Tuple<ActorData, Coordinates>> myActorLocationTuples;
	
	public BasePlacementData(){
		myActorLocationTuples = new ArrayList<>();
	}
	
	public BasePlacementData(List<Tuple<ActorData, Coordinates>> actorAndLocation){
		myActorLocationTuples = actorAndLocation;
	}

	public List<Tuple<ActorData, Coordinates>> getMyActorToLocation() {
		return myActorLocationTuples;
	}
	
	public void addBase(Tuple<ActorData, Coordinates> t){
		this.myActorLocationTuples.add(t);
	}
	
	public void removeBase(Tuple<ActorData, Coordinates> t){
		this.myActorLocationTuples.remove(t);
	}
	
	
}
