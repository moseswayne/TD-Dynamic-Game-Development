package XML.xmlmanager.interfaces.serialization;

import XML.xmlmanager.exceptions.IllegalXStreamCastException;

/**
 * @author Gideon
 * 
 * This interface is designed to be simple and straight forward. 
 * It is capable of both making Objects from String XML passed as
 * well as making String XML from objects passed. A Concrete example
 * is {@link  XStreamSerializer}.
 *
 */
public interface VoogaSerializer{
	
	/**
	 * @param o the object you want to serialize
	 * @return the String XML representation of the serialized object
	 */
	String getXMLStringFromObject(Object o);
	
	/**
	 * @param XMLString the String to be make back into an object
	 * @param clazz the class to cast the object to before returning it
	 * @return an instance of clazz based on the xml string passed
	 * @throws IllegalXStreamCastException an exception thrown when the xml String
	 * could not be converted into the class of interest
	 */
	<C> C makeObjectFromXMLString(String XMLString, Class<C> clazz) throws IllegalXStreamCastException;

}
