package util.observerobservable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sarahzhou
 * 
 * Specific implementation of VoogaObservable of a generic Map data structure
 * @param <K>
 * @param <V>
 */
public class VoogaObservableMap<K,V> extends VoogaObservable<Map<K,V>> {

	protected Map<K,V> myMap;
	
	public VoogaObservableMap() {
		super();
		myMap = new HashMap<K,V>();
	}
	
	@Override
	protected Map<K, V> notification() {
		return Collections.unmodifiableMap(myMap);
	}

}
