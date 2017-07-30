package gamedata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameengine.grid.interfaces.Identifiers.Grid2D;
import types.BasicActorType;

/**
 * 
 * GameData is the over-arching data class that holds 
 * all of the data required to launch a game.
 * It holds a Map of each actor type between
 * Integers (representing Option numbers) and ActorDatas, 
 * where the ActorData holds information about how to
 * make that Actor.
 * 
 * The LevelData objects hold preferences for each
 * Level -- these are saved and passed to the LevelController
 * constructor whenever a new Level is created.
 * 
 * USES:
 * 
 * GAME PLAYER:
 * -getOptions --> return all pieces (ActorData) mapped to order numbers (Integers)
 * -getTowerOptions --> return all Tower pieces
 * -getTroopOptions --> return all Troop pieces
 * -getBaseOptions --> return all Base pieces
 * -getShotOptions --> return all Shot pieces
 * 
 * GAME CONTROLLER:
 * -getOption(Integer index) --> return the ActorData associated with that order number
 * 
 * 
 * GAME AUTHORING ENVIRONMENT:
 * -add(ActorData a) --> Add an ActorData (blueprint for an Actor) 
 * 			to the current representation of the game
 * -addLevel(LevelData l) --> Add a LevelData to represent the preferences and 
 * 			enemies for that level
 * 
 * 
 * @author maddiebriere
 *
 */

public class GameData {
	String name;
	//Level information (preferences, no & type of enemies)
	Map<Integer,LevelData> levels;
	
	//Preferences for game
	PreferencesData preferences;
	
	//Layer information
	MapLayersData myLayers; 

	//Categories available (e.g. Troop)
	List<BasicActorType> types;
	
	//Information about how the game is visually displayed
	DisplayData display;
	
	//Actors available for entire game
	private Map<Integer, LineageData> pieces;
	
	//References the location of the last index
	private int numOptions;


	public GameData(){
		this("");
	}
	
	public GameData(String name){
		this.name = name;
		levels=new HashMap<Integer,LevelData>();
		preferences = new PreferencesData();
		display = new DisplayData();
		pieces = new HashMap<Integer, LineageData>();
		types = new ArrayList<BasicActorType>();
		myLayers = new MapLayersData();
		numOptions = 0;
	}
	
	public Map<Integer, LineageData> getMappedLineageData(){
		return pieces;
	}
	
	/**
	 * This is for use in the GamePlayer. Returns
	 * all of possible options for creation in the 
	 * game.
	 */
	public Map<Integer, ActorData> getOptions(){
		HashMap<Integer, ActorData> toRet = new HashMap<Integer, ActorData>();
		for(Integer lineage: pieces.keySet()){
			toRet.put(lineage, pieces.get(lineage).getCurrent());
		}
		return toRet;
	}
	public Collection<LineageData> getLineageData(){
		return pieces.values();
	}
	
	/**
	 * Get all option matching to a certain type (Troop, Tower, etc.) of
	 * Actor. 
	 * @param type Type to match
	 * @return All Actors available matching this type
	 */
	public Map<Integer,ActorData> getAllOfType(BasicActorType type){
		Map<Integer,ActorData> toRet = new HashMap<Integer,ActorData>();
		getOptions().forEach((key, value) 
				-> {if (value.getType().equals(type)) { 
					toRet.put(key,value);
					}});
		return toRet;
	}
	
	/**
	 * Map of String (name of Lineage) to LineageData
	 * @param type BasicActorType to filter by
	 * @return
	 */
	public Map<String, LineageData> getAllLinOfType(BasicActorType type){
		Map<String,LineageData> toRet = new HashMap<String,LineageData>();
		for(LineageData data: getMappedLineageData().values()){
			ActorData lead= data.getCurrent();
			if(lead.getType().equals(type)){
				toRet.put(lead.getName(), data);
			}
		}
		return toRet;
	}
	
	/**
	 * Remove a general category from the GameData (e.g., Projectile)
	 * 
	 * @param actor BasicActorType to remove
	 */
	public DisplayData getDisplayData(){
		return display;
	}
	public void setDisplayData(DisplayData data){
		display=data;
	}
	public void removeCategory(BasicActorType actor){
		types.remove(actor);
	}
	
	/**
	 * Remove a LineageData, including all of its actors (e.g., Snorlax)
	 * 
	 * @param actor LineageData to remove
	 */
	public void removeLineage(LineageData actor){
		for(Integer gen: pieces.keySet()){
			if(pieces.equals(actor)){ //equals method overriden in LineageData
				pieces.remove(gen);
			}
		}
	}
	
	/**
	 * Completely remove any traces of an ActorData from the GameData,
	 * including references in the LevelData objects
	 * @param actor
	 */
	public void completeWipeLineage(LineageData lin){
		removeLineage(lin);
		for(ActorData actor: lin.getMap().values()){
			removeFromLevels(actor);
		}
	}
	
	private void removeFromLevels(ActorData actor){
		if(levels.size()==0){
			return;
		}
		for (int i=0; i<levels.size(); i++){
			List<WaveData> waves = new ArrayList<WaveData>();
			try{
				waves = levels.get(i).getMyWaves();
			}catch(Exception e){
				break;
			}
			for(int j=0; j<waves.size(); j++){
				WaveData wave = waves.get(j);
				wave.removeActor(actor);
			}
		}
	}

	
	/**
	 * This is for use in the GameController.
	 * 
	 * This returns you the ActorData matching to 
	 * the requested option. This ActorData can then be
	 * passed to ActorGenerator in order to create the Actor.
	 * 
	 * @param option Integer representing the option
	 * @return ActorData mapping to that option
	 */
	public ActorData getOption(Integer option){
		return getOptions().get(option);
	}
	
	public Integer getOptionKey(ActorData actor){
		for(Integer option: pieces.keySet()){
			if(pieces.get(option).getProgenitor().equals(actor)){
				return option;
			}
		}
		return 0;
	}
	
	
	/**
	 * This is implementation for use in the Authoring Environment
	 * 
	 * It allows the front-end to add another List of
	 * Data objects representing a possible object
	 * 
	 * Frontend must make an ActorData object and pass it in.
	 * 
	 * See ActorData for an example of how to
	 * create and ActorData object
	 * 
	 */
	public LineageData add(ActorData data){
		LineageData lin = new LineageData(data);
		add(lin);
		return lin;
	}
	
	public void add(LineageData data){
		pieces.put(numOptions, data);
		numOptions++;
	}
	
	public void addUpgrade(LineageData data, ActorData toAdd){
		data.addGeneration(toAdd);
	}
	
	/**
	 * Add another category type -- defined by user.
	 * Example: addType("Monster")
	 * @param newCategory New category
	 * @return BasicActorType Created category
	 */
	public BasicActorType addType(String newCategory){
		BasicActorType toRet = new BasicActorType(newCategory);
		types.add(toRet);
		return toRet;
	}
	
	/**
	 * Easy way to add a level -- just pass in
	 * the enemies used in this level.
	 * 
	 * Integer maps to the number of enemies on the level.
	 * 
	 * @param duration representing level length
	 */
	public void addLevel(double duration,int level){
		levels.put(level,new LevelData(duration));
	}
	
	/**
	 * More sophisticated way to add a level -- pass entire
	 * level data with preferences, settings and troops
	 * encapsulated in data structure
	 * 
	 * @param data LevelData holding level information
	 */
	public void addLevel(LevelData data, int level){
		levels.put(level,data);
	}
	
	
	/**
	 * Returns Integers corresponding to a path number, matched to the 
	 * Path that is defined by that number.
	 * 
	 * @return Map of Integers mapped to Paths
	 */
	public Map<Integer, List<Grid2D>> getPathOptions(){
		return myLayers.getMyPathData().getMyPaths();
	}
	
	
	//Getters and setters
	public Map<Integer,LevelData> getLevels() {
		return levels;
	}
	public LevelData getLevel(int level){
		return levels.get(level);
	}
	public void setLevel(Map<Integer,LevelData> level) {
		this.levels = level;
	}

	public int getNumOptions() {
		return numOptions;
	}

	public void setNumOptions(int numOptions) {
		this.numOptions = numOptions;
	}

	public PathData getMyPaths() {
		return myLayers.getMyPathData();
	}

	public void setMyPaths(PathData pathData) {
		this.myLayers.setMyPathData(pathData);
	}

	public List<BasicActorType> getTypes() {
		return types;
	}

	public void setTypes(List<BasicActorType> types) {
		this.types = types;
	}

	public PreferencesData getPreferences() {
		return preferences;
	}

	public void setPreferences(PreferencesData preferences) {
		this.preferences = preferences;
	}
	
	public void setLayers(MapLayersData layers){
		myLayers = layers;
	}
	public MapLayersData getLayers(){
		return myLayers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
