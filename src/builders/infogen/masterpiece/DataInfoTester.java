package builders.infogen.masterpiece;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Minimal tester to confirm the functionality of the DataInfoGenerator.
 * Checks to confirm that the concrete classes are being found and used
 * correctly, and other such conditions.
 * 
 * @author maddiebriere
 * d
 */

public class DataInfoTester {
	
	/**
	 * Test the functionality of the concrete class
	 * method in the DataInfoGenerator
	 */
	@Test
	public void testConcreteClasses(){
		for(Class<?> clzz: getConcreteClasses()){
			assertNotEquals(null, generateNewInstance(clzz));
		}
	}
	
	/**
	 * Test the simplify and rebuild methods
	 */
	@Test
	public void testDataRebuild(){
		List<Class<?>> classes = getConcreteClasses();
		List<String> simplified = getDataInfo().getSimplifiedNames(classes);
		DataInfoGenerator data = getDataInfo();
		List<Class<?>> rebuilt = simplified
				.stream()
				.map(p -> data.rebuildFromName(p))
				.collect(Collectors.toList());
		assertEquals(rebuilt.size(), classes.size());
		for(int i=0; i<classes.size(); i++){
			assertEquals(classes.get(i), rebuilt.get(i));
		}
	}
	
	private Object generateNewInstance(Class<?> clzz){
		Object obj;
		try {
			obj = clzz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			obj = null;
		}
		return obj;
	}
	
	private List<Class<?>> getConcreteClasses(){
		return getDataInfo().getConcreteDataClasses();
	}
	
	private DataInfoGenerator getDataInfo(){
		return new DataInfoGenerator();
	}
 	
}
