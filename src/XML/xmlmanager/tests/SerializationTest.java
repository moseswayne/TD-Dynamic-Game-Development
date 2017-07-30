package XML.xmlmanager.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import XML.xmlmanager.classes.XStreamSerializer;
import XML.xmlmanager.exceptions.IllegalXStreamCastException;
import XML.xmlmanager.interfaces.serialization.VoogaSerializer;
import gamedata.GameData;
import types.BasicActorType;

public class SerializationTest {
	
	private GameData myData;
	private VoogaSerializer mySerializer;
	
    public void setUp () {
       myData = new GameData();
       mySerializer = new XStreamSerializer();
       myData.addType("test1");
       myData.addType("test2");
    }

    // adds data to the object, serializes it, then makes sure the data was preserved
	@Test
	public void BasicWriteAndReadTest() {
		setUp();
		String myDataString = mySerializer.getXMLStringFromObject(myData);
		try{
			myData = mySerializer.makeObjectFromXMLString(myDataString, GameData.class);
		} catch(IllegalXStreamCastException ex){
			Assert.fail("Test failed : " + ex.getCauseException().getMessage());
		}
		List<BasicActorType> types = myData.getTypes();
		assertEquals(types.size(), 2);
		Assert.assertTrue(types.contains(new BasicActorType("test1")));
		Assert.assertTrue(types.contains(new BasicActorType("test2")));
	}
	
    // tries to get an invalid class from an XML String
	@Test
	public void InvalidCastTest() {
		setUp();
		String myDataString = mySerializer.getXMLStringFromObject(myData);
		try{
			mySerializer.makeObjectFromXMLString(myDataString, String.class);
			Assert.fail("Test failed : no exception was thrown during illegal cast");
		} catch(IllegalXStreamCastException ex){
			
		}
	}
	
    // sends an invalid String into the object generator
	@Test
	public void InvalidWrite() {
		setUp();
		try{
			myData = mySerializer.makeObjectFromXMLString("sdfasdf", GameData.class);
			Assert.fail("Test failed : no exception was thrown during illegal cast");
		} catch(IllegalXStreamCastException ex){

		}
	}
}