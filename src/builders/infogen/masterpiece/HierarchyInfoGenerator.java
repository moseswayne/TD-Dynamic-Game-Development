// This entire file is part of my masterpiece.
// Maddie Briere

package builders.infogen.masterpiece;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import builders.infogen.VarArgsUtil;
import builders.util.FieldGenerator;
import gamedata.FieldData;

/**
 * This is a more specific extension of PackageInfoGenerator,
 * in which we assume that the user is passing in String package
 * paths in an ordered fashion, with the first String representing
 * the most concrete package (like leaf nodes) and the last String
 * representing the most abstracted package. This allows us to make
 * simplifying assumptions when creating objects and such.
 * 
 * This file is included in my masterpiece to demonstrate:
 * 
 * 1) How added functionality can be added to an abstract class via inheritance,
 * allowing us to create hierarchies of increasingly more specific purpose. Specifically, 
 * this demostrates how PackageInfoGenerator can be extended for more targeted
 * package searching.
 * 
 * 2) This class eliminates the duplicated code between the DataInfoGenerator
 * class and ConditionInfoGenerator class, reducing these classes to short 
 * implementations.
 * 
 * 
 * @author maddiebriere
 *
 */

public abstract class HierarchyInfoGenerator extends PackageInfoGenerator{
	
	/**
	 * The user should enter a String corresponding to the lower level (most
	 * concrete) package of classes (ex. gamedata.composition) and a String
	 * corresponding to the higher level (most abstract of classes
	 * (ex. TwoLevelInfoGenerator)
	 * @param concreteLevel
	 * @param categoryLevels
	 */
	public HierarchyInfoGenerator(String concreteLevel, String ... categoryLevels) {
		super(VarArgsUtil.createVarArgs(concreteLevel, categoryLevels));
	}
	
	/**
	 * Get all of the property types available to the user. 
	 * Includes:
	 * - LimitedHealth
	 * - MoveWithDestination
	 * - ShotTargetFar
	 * ...
	 * @return List of String corresponding to the names of the properties
	 */
	public List<String> getOptionNames(){
		return packageNameSearch(0);
	}
	
	/**
	 * Get all of the property categories available to the user. 
	 * Includes:
	 * - Shoot
	 * - Move
	 * - Health
	 * ...
	 * @return List of String corresponding to the names of the properties
	 */
	public List<String> getCategoryNames(){
		return packageNameSearch(packages.size()-1);
	}
	
	/**
	 * Return a map of the Strings representing the class type and a List of
	 * FieldData objects representing the fields taken by that class.
	 * 
	 * @param info TwoLevelInfoGenerator for use in collecting concrete data classes
	 * @return Map of String names representing classes to Lists of FieldDatas
	 */
	public Map<String, List<FieldData>> getOptionsWithArgs(){
		List<Class<?>> datas = getConcreteDataClasses();
		Map<String,List<FieldData>> toRet = new LinkedHashMap<String, List<FieldData>>();
		
		for(Class<?> clzz: datas){
			List<Field> fields = FieldGenerator.getFields(clzz);
			List<FieldData> fieldDatas = FieldGenerator.getFieldDatas(fields);
			toRet.put(simplifyName(clzz), fieldDatas);
		}
		return toRet;
	}
	
	/**
	 * Get all of the data types available in the back-end 
	 * Includes:
	 * - LimitedHealthData
	 * - MoveWithDestinationData
	 * - ShotTargetFarData
	 * ...
	 * @return List of String corresponding to the names of data classes
	 */
	public List<Class<?>> getConcreteDataClasses(){
		return allClassesIn(0);
	}
	
	/**
	 * Warning: ADDING THIS PACKAGE ASSUMES THAT THIS IS THE MOST
	 * GENERAL PACKAGE (as it will be appended to the end of the 
	 * package list).
	 * 
	 * This particular implementation has no restrictions on adding
	 * packages, as long as the assumptions are minded.
	 * @param pkg Package to add
	 * @return true (package will always be added)
	 */
	@Override
	public boolean addPackage(String pkg) {
		return packages.add(pkg);
	}

	/**
	 * Only removes the given package if it exists
	 * in packages and if the list will have at least
	 *  one element in it when pkg is removed.
	 *  @param pkg Package path to remove
	 *  @return true if the package was removed, false otherwise
	 */
	@Override
	public boolean removePackage(String pkg) {
		if(packages.size()>1){
			return packages.remove(pkg);
		}
		return false;
	}
}
