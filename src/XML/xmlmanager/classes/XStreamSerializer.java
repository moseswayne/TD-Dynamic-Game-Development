package XML.xmlmanager.classes;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import XML.xmlmanager.exceptions.IllegalXStreamCastException;
import XML.xmlmanager.interfaces.serialization.VoogaSerializer;
/**
 * 
 * @author Gideon
 *
 * This class is a concrete implementation of the {@link VoogaSerializer} interface
 * 
 * It converts objects to String XML and converts them back to objects
 */
public class XStreamSerializer implements VoogaSerializer{
	
	private XStream xstream;
	
	public XStreamSerializer(){
		xstream = new XStream(new DomDriver());
	}

	/**
	 * returns the String XMl representation of the the object based on XStream serialization
	 * 
	 * See {@link VoogaSerializer}
	 */
	@Override
	public String getXMLStringFromObject(Object o) {
		return xstream.toXML(o);
	}

	/**
	 * Converts String into an object using the XStream to object methods. Casts the object
	 * to the class passed in as well
	 * 
	 * See {@link VoogaSerializer}
	 */
	@Override
	public <C> C makeObjectFromXMLString(String XMLString, Class<C> clazz) throws IllegalXStreamCastException{
		try{
			return clazz.cast(xstream.fromXML(XMLString));
		} catch(Exception ex){
			throw new IllegalXStreamCastException(ex);
		}
	}
}
