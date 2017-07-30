package builders.infogen;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import builders.infogen.masterpiece.DataInfoGenerator;
import builders.infogen.masterpiece.HierarchyInfoGenerator;
import builders.util.FieldGenerator;
import gamedata.FieldData;
import gamedata.GameData;
import gamedata.compositiongen.Data;
import gameengine.conditionsgen.Condition;
import util.PropertyUtil;

/**
 * 
 * Information generator for use in the Game Authoring Environment. 
 * Generates information about what types of properties and
 * actors can be built (for use in displaying options
 * to user via buttons or drop-down menus).
 * 
 * @author maddiebriere
 *
 */

public class AuthorInfoGenerator{
	
	/**
	 * Returns currently defined Strings for
	 * categories (e.g., Monster, Troop) in the 
	 * given GameData object.
	 * 
	 * @param data Current GameData object
	 * @return Current available categories in the game
	 */
	public static List<String> getActorTypes(GameData data){
		return data.getTypes()
				.stream()
				.map(p-> p.getType())
				.collect(Collectors.toList());
	}
	
	/**
	 * Generate the simple String name of a Data object
	 * using the DataInfoGenerator class.
	 * 
	 * @param data Data to convert to a String name
	 * @return String name representing the Data object
	 */
	public static String getName(Data data){
		return (new DataInfoGenerator()).simplifyObjectName(data);
	}
	
	
	/**
	 * Generate the simple String name of a Condition object
	 * using the ConditionInfoGenerator class.
	 * 
	 * @param con Condition to convert to a String name
	 * @return String name representing the Condition object
	 */
	public static String getName(Condition con){
		return (new ConditionInfoGenerator()).simplifyObjectName(con);
	}
	
	/**
	 * Get all of the Property types (as Strings) matched to a List of
	 * FieldData objects representing the fields required by that property.
	 * 
	 * Example:
	 * Command: getPropertyTypesWithArgs()
	 * Returns: 
	 * 
	 * LimitedHealth
	 * - double startHealth
	 * 
	 * ShootTargetFar
	 * - double myRange
	 * - int  fireRate
	 * 
	 * ...
	 * 
	 * @return Map of String names (representing individual
	 * property types) to arguments taken in that class
	 */
	public static Map<String, List<FieldData>> getPropertyTypesWithArgs(){
		return getTypesWithArgs(new DataInfoGenerator());
	}
	
	/**
	 * Get all of the Condition types (as Strings) matched to a List of
	 * FieldData objects representing the fields required by that condition.
	 * 
	 * Example:
	 * Command: getConditionTypesWithArgs()
	 * Returns: 
	 * 
	 * Endurance
	 * - double duration
	 * 
	 * KillAll
	 * - EMPTY
	 * 
	 * @return Map of String names (representing individual
	 * condition types) to arguments taken in that class
	 */
	public static Map<String, List<FieldData>> getConditionTypesWithArgs(){
		return getTypesWithArgs(new ConditionInfoGenerator());
	}
	
	/**
	 * Return a map of the Strings representing the class type and a List of
	 * FieldData objects representing the fields taken by that class.
	 * 
	 * @param info TwoLevelInfoGenerator for use in collecting concrete data classes
	 * @return Map of String names representing classes to Lists of FieldDatas
	 */
	private static  Map<String, List<FieldData>> getTypesWithArgs
			(HierarchyInfoGenerator info){
		List<Class<?>> datas = info.getConcreteDataClasses();
		for(Class<?> data: datas){
			System.out.println(data.getSimpleName());
		}
		Map<String,List<FieldData>> toRet = new LinkedHashMap<String, List<FieldData>>();
		
		for(Class<?> clzz: datas){
			List<Field> fields = FieldGenerator.getFields(clzz);
			List<FieldData> fieldDatas = FieldGenerator.getFieldDatas(fields);
			toRet.put(info.simplifyName(clzz), fieldDatas);
		}
		return toRet;
	}
	
	/**
	 * Get the fields of a data object using the FieldGenerator.
	 * 
	 * NOTE: There is a similar method in the VOOGA util package. See the 
	 * FieldGenerator class for comments on this.
	 * 
	 * @param data Data object from which to get fields
	 * @return Map of Strings (names of fields) to Objects (field values)
	 */
	public static Map<String, Object> getFields(Data data){
		return FieldGenerator.getFields(data);
	}
	
	/**
	 * Returns a String defining the property. If there is no
	 * String for the specific property, if will look for a description
	 * for supertypes.
	 * 
	 * @param propertyName Property name (e.g., ShootMulti)
	 * @return description A String defining the property
	 */
	public static String getDescription(String propertyName){
		return PropertyDescriptionGenerator.getDescription(propertyName);
	}
}
