package gamedata;

import java.util.ArrayList;
import java.util.List;

import gamedata.compositiongen.Data;
import gamedata.map.PolygonData;

/**
 * @author TNK
 * @author Anh
 *
 */
public class LayerData {
	
	private List<PolygonData> myPolygons;
	
	public LayerData(List<PolygonData> polygons){
		myPolygons = polygons;
	}
	
	/**
	 * default for LayerData: only has 1 polygon which is the whole screen
	 */
	public LayerData(){
		myPolygons = new ArrayList<PolygonData>();
//		myPolygons.add(new PolygonData());
	}
	
	public List<PolygonData> getMyPolygons(){
		return myPolygons;
	}
	
	
}
