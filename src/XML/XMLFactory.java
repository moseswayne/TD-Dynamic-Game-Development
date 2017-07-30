package XML;

import gamedata.GameData;

public interface XMLFactory {
	
	public String toXML(GameData data);
	
	public GameData fromXML(String translate);
}
