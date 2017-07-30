package XML.directorytree.classes;

import XML.directorytree.interfaces.Pair;

/**
 * 
 * @author Gideon 
 *
 * This class is designed to be an alternative to AbstractMap.SimpleEntry
 * 
 * It is a pretty basic implementation of getters and setters for keys and values
 * 
 * @param <K> The type of the Key to be stored
 * @param <V> The type of the value to be stored
 */
public class ConcretePair <K, V> implements Pair<K, V>{
	
	private K key;
	private V value;

	public ConcretePair(K key, V value){
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Returns the key
	 * 
	 * See {@link Pair}
	 */
	@Override
	public K getKey() {
		return key;
	}

	/**
	 * Returns the value
	 * 
	 * See {@link Pair}
	 */
	@Override
	public V getValue() {
		return value;
	}

	/**
	 * Sets the key
	 * 
	 * See {@link Pair}
	 */
	@Override
	public void setKey(K k) {
		this.key = k;
	}

	/**
	 * Sets the value
	 * 
	 * See {@link Pair}
	 */
	@Override
	public void setValue(V v) {
		this.value = v;
	}

}
