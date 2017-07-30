package gameengine.actors.properties;

import gamedata.composition.LimitedHealthData;

public class ImmuneHealthProperty extends LimitedHealthProperty{
	
	public ImmuneHealthProperty(LimitedHealthData data) {
		super(data);
	}

	@Override
	public void apply(double damage) {
		
	}
	
}
