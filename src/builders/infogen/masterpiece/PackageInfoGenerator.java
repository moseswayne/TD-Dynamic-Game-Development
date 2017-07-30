// This entire file is part of my masterpiece.
// Maddie Briere

package builders.infogen.masterpiece;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gamedata.reflections.Reflections;
import voogasalad.util.reflection.ReflectionException;

/**
 * Abstract class used to garner information from a given package.
 * This generator can iterate through a package and return things like:
 * --> Classes within the package
 * --> Simplified names of packages within the package
 * --> A way to "rebuild" and "simplify" names and classes
 * 
 * This class has been included in my masterpiece because it demonstrates the following:
 * _____________________________
 * 1) This use of an abstract superclass allows us to define what functionality we want by
 * creating a set of abstract methods to be defined by subclasses. The intention is to create
 * a class that is closed to modification (i.e., we shouldn't have to modify the code in this 
 * superclass) but open to extension (via subclasses) per the Open-Closed Principle
 * 
 * 2) This class was initially built to avoid hard-coding. We wanted to be able to produce 
 * information about available options for the authoring environment, but adding all of this
 * information in a resource file would have required an extra hoop to jump through when creating
 * things like Properties and Data classes. Instead, I opted for this solution: "mining" the
 * information at runtime.
 * 
 * 3) This class demonstrates how other sources can be used to create functional code. Wherever 
 * tested and well-written code can be substituted, this is ideal! Incorporating the code
 * of others displays flexibility and helps minimize future bugs. This can be seen in the use
 * of the ReflectionException written by Duvall (found in the VoogaUtil package) and the Reflections
 * package, inspired from an external source (see the Reflections class for more information), which is 
 * used for package searching.
 * 
 * 4) This class is well-documented -- this is important when working in such
 * a large team environment, as others may rely on your code and have to make
 * assumptions if you do not state all of the information necessary for use of the
 * feature's methods.
 * 
 * 
 * FUN NOTES:
 * _____________________________
 * a) This class uses wild-card generics to allow the user to pass classes
 * with any type of generic (of an unknown type) to get around having to
 * define explicitly the type of class allowed. More at:
 * http://docs.oracle.com/javase/tutorial/extra/generics/wildcards.html
 * 
 * b) This class also uses variable arguments, to allow for heightened flexibility --
 * implementing subclasses are free to limit these inputs!
 * 
 * @author maddiebriere
 *
 */

public abstract class PackageInfoGenerator {
	List<String> packages;
	
	public PackageInfoGenerator(String ... packages){
		this.packages = Arrays.asList(packages);
	}
	
	/**
	 * Modify an input class name to return a displayable
	 * String. 
	 * 
	 * Example for AuthorInfoGenerator:
	 * Input: gamedata.composition.MoveWithSetPathData
	 * Output: MoveWithSetPath
	 * 
	 * @param name className to simplify -- assuming labeling system is
	 * consistent across packages
	 * @return Simplified class name
	 */
	public abstract String simplifyName(Class<?> className);
	
	/**
	 * Return the simplified class name of the given object.
	 * Instead of returning a full class path, return 
	 * the simple name modified using the given parameters.
	 * 
	 * @param obj Object from which to collect simplified class name
	 * @return Simplified name
	 */
	public<T> String simplifyObjectName(T obj){
		Class<?> clzz = obj.getClass();
		return simplifyName(clzz);
	}
	
	/**
	 * Return the rebuilt address from the given name, for use
	 * in reflection.
	 * 
	 * @param name Simple name (e.g., MoveWithSetPath)
	 * @return String address (gamedata.composition.MoveWithSetPathData)
	 */
	protected abstract String rebuildAddress(String name);
	
	/**
	 * Method to add a package given a String path to the
	 * given package. This is abstract to allow for subclasses
	 * to either dis-allow package adding or restrict it.
	 * @param pkg String path to package
	 * @return true if the package was added, false otherwise
	 */
	public abstract boolean addPackage(String pkg);
	
	/**
	 * Method to remove a package given a String path to the
	 * given package. This is abstract to allow for subclasses
	 * to either dis-allow package removal or restrict it.
	 * @param pkg String path to package
	 * @return true if the package was removed, false otherwise
	 */
	public abstract boolean removePackage(String pkg);
	
	/**
	 * Use the simplifyName method to return a List of simplified names
	 * matching to a list of classes.
	 * 
	 * @param toSimplify List of Classes to simplify
	 * @return List of Strings representing simplified class names
	 */
	public List<String> getSimplifiedNames(List<Class<?>> toSimplify){
		return 	toSimplify
				.stream()
				.map(p -> simplifyName(p))
				.collect(Collectors.toList());
	}
	
	/**
	 * Modify an input target name to reflect
	 * an actual Class name.
	 * 
	 * Example: For DataInfoGenerator
	 * Input: "MoveWithSetPath"
	 * Output: Class<MoveWithSetPathData>
	 * 
	 * @param name String to "rebuild" into valid class
	 * @return Valid class name matching to given keyword
	 */
	public Class<?> rebuildFromName(String name) {
		try {
			return Class.forName(rebuildAddress(name));
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * This method returns a List of all of the classes in the given
	 * package path, or throws an error if the package is not found.
	 * 
	 * @param pkg String address to path
	 * @return List of classes in package
	 */
	protected List<Class<?>> allClassesIn(String pkg){
		Class<?> [] properties = new Class[0]; 
		try {
			properties = Reflections.getClasses(pkg);
		} catch (ClassNotFoundException | IOException e) {
			throw new ReflectionException("Package not found", this);
		}
		return Arrays.asList(properties);
	}
	
	/**
	 * This method returns a List of all of the classes in the given 
	 * package, where the level is a number corresponding to 
	 * the expected package (where 0 is the first added, the lowest level, 
	 * and packages.size()-1 is the last added, the highest level).
	 * 
	 * @param level Integer corresponding to expected package
	 * @return A List of Classes in the given package or an empty
	 * List (if the package is not referenced in packages)
	 */
	protected List<Class<?>> allClassesIn(int level){
		if(packages.size()>level)
			return allClassesIn(packages.get(level));
		else
			return new ArrayList<Class<?>>();
	}
	
	/**
	 * Search the given package (corresponding the the integer level) 
	 * and returns the simplified names for the found classes.
	 * 
	 * @param level The integer corresponding to the expected level
	 * @return a List of Strings corresponding to the classes in the level
	 */
	protected List<String> packageNameSearch(int level){
		List<Class<?>> types = allClassesIn(level);
		if(!(types.size() == 0)){
			return getSimplifiedNames(types);
		}
		return new ArrayList<String>();
	}

	/**
	 * Getter for packages, returns COPY of packages to
	 * avoid alterations
	 * 
	 * @return A copy of the packages
	 */
	public List<String> getPackages() {
		return new ArrayList<String>(packages);
	}
}
