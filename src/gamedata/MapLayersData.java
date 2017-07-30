package gamedata;

import java.util.HashMap;
import java.util.Map;


/**
 * MapData holds information about layers of specific polygons on which actors
 * can be placed The basic implementation is a Map of Integer to LayerData (an
 * object holding a list of polygons defining spaces within the map)
 * 
 * Use the PlaceableData object to specify which layer an actor can be placed on
 * 
 * Example usage: Add a land layer and water layer. Then separate the tower
 * actors into ones that can be placed on water and ones that can be placed on
 * land.
 * 
 * @author TNK
 *
 */
public class MapLayersData {
	
	private PathData myPathData;
	private Map<String, LayerData> myLayers;
	private BasePlacementData myBaseData;
	
	public MapLayersData() {
		myLayers = new HashMap<>();
		setMyBaseData(new BasePlacementData());
		setMyPathData(new PathData());
	}

	/**
	 * use this method to place a new layer into the mapdata index is given
	 * automatically
	 * 
	 * @param name
	 *            the name for the layer
	 * @param layer
	 *            a layer data object
	 */

	public void addLayer(String name, LayerData layer) {
		myLayers.put(name, layer);
	}

	/**
	 * meant to be used only with the authoring environment Purpose is to remove
	 * a layer
	 * 
	 * @param layer
	 */
	public void removeLayer(LayerData layer) {
		myLayers.entrySet().stream().forEach( entry -> {
			if(entry.getValue()== layer){
				myLayers.remove(entry.getKey());
			}
		});
	}

	/**
	 * returns the actual layerdata object for sub UI 
	 * components to modify
	 * @return
	 */
	public Map<String, LayerData> getMyLayers() {
		return myLayers;
	}

	public PathData getMyPathData() {
		return myPathData;
	}

	public void setMyPathData(PathData myPathData) {
		this.myPathData = myPathData;
	}

	public BasePlacementData getMyBaseData() {
		return myBaseData;
	}

	public void setMyBaseData(BasePlacementData myBaseData) {
		this.myBaseData = myBaseData;
	}

}
