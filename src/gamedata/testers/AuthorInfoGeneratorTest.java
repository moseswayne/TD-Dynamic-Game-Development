package gamedata.testers;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import builders.infogen.AuthorInfoGenerator;
import builders.objectgen.DataGenerator;
import gamedata.FieldData;
import gamedata.GameData;
import gamedata.compositiongen.Data;
import types.BasicActorType;

//import java.lang.reflect.Field;

/**
 * Test AuthorInfoGenerator
 * @author maddiebriere
 *
 */

public class AuthorInfoGeneratorTest {

	public void correctChoices() {
		GameData data = new GameData();
		data.addType("Bear");
		data.addType("Monster");
		data.addType("HI");
		assertEquals(data.getTypes().size(), 3);
		assertEquals(data.getTypes().get(0), new BasicActorType("Bear"));
	}
	
	public void fieldPrint(){
	}

	@Test
	public void correctPropertyArgs(){
		Map<String, List<FieldData>> options = AuthorInfoGenerator.getPropertyTypesWithArgs();
		for(String s: options.keySet()){
			System.out.println(s + " ");
			for(FieldData f: options.get(s)){
				System.out.println(f.getMyType() + " " + f.getMyName());
			}
			System.out.println();
		}
	}

	
	public void getName(){
		Data d = DataGenerator.makeData("LimitedHealthData", 10.0);
		assertEquals("LimitedHealth", AuthorInfoGenerator.getName(d));
	}
	 
	public void getFields(){
		System.out.println("\n");
		Data d = DataGenerator.makeData("LimitedHealthData", 10.0);
		Map<String, Object> values = AuthorInfoGenerator.getFields(d);
		for(String s: values.keySet()){
			System.out.println(s + " " + values.get(s));
		}
		System.out.println("\n");
		//assertEquals(10.0, o);
	}
	
	public void testPropertyDescriptions(){
		System.out.println(AuthorInfoGenerator.getDescription("BaseDamage"));
		System.out.println(AuthorInfoGenerator.getDescription("Shoot"));
		System.out.println(AuthorInfoGenerator.getDescription("Shootsfldldjfhgl"));
	}

}
