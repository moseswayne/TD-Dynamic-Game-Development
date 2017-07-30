package XML.directorytree.interfaces;

/**
 * 
 * @author Gideon
 *
 * Designed to be used as an alternative to AbstractMap.SimpleEntry()
 * See {@link ConcretePair)
 * for a concrete implementation. 
 *
 * @param <K> the type of key you want stored
 * @param <V> the value you want stored
 */
public interface Pair <K, V> {

	/**
	 * @return the key stored
	 */
	K getKey();
	
	/**
	 * @return the value stored
	 */
	V getValue();
	
	/**
	 * @param k sets the key 
	 */
	void setKey(K k);
	
	/**
	 * @param v sets the value
	 */
	void setValue(V v);
	
}
