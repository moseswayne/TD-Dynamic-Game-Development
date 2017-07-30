package ui.handlers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import gamedata.ActorData;
import gamedata.DisplayData;
import gamedata.LineageData;
import util.InsufficientMoneyException;
import util.LayerNotPlaceableException;
import util.VoogaException;

/**
 * UIHandler that specifies methods front end can call to evoke changes in back end, and get information from back end
 * @author sarahzhou
 *
 */
public interface UIHandler {
	
	void deleteGameObject(int id);
	void updateGameObjectType(int id, Integer currentOption) throws VoogaException, InsufficientMoneyException;
	void updateGameObjectLocation(int id, double x, double y) throws VoogaException;
	int addGameObject(Integer option, double x, double y) throws VoogaException, LayerNotPlaceableException, InsufficientMoneyException;
	Map<Integer,ActorData> getOptions();
	Map<Integer,LineageData> getLineageData();
	DisplayData getDisplayData();
	void launchGame() throws VoogaException;
	void changeLevel(int level) throws VoogaException;
}