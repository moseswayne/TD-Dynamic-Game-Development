// This entire file is part of my masterpiece.
// Maddie Briere

package builders.infogen.masterpiece;

import builders.infogen.ClassFinder;

/**
 * Generates information about Data objects, including
 * concrete subclasses, abstract super classes, etc.
 * (inherited).
 * 
 * This is included in my masterpiece to demonstrate a concrete
 * implementation of the HierarchyInfoGenerator. This is included 
 * to demonstrate:
 * 
 * 1) Because of the specific implementation of this abstract class,
 * the concrete class DataInfoGenerator, which is really doing
 * all of the work, has extremely simplistic and easy to follow 
 * methods.
 * 
 * 2) Instead of saving the file paths to the
 * relevant data packages in this class, I have exported these to
 * a property file, using the DATA and SUPER_DATA keys to access these
 * paths. As a result, should any of these paths change, the location of 
 * their record can easily be found. This contributes to this
 * being a closed class (not requiring modification) because any
 * changes to packages will be exported to a supporting file, not
 * this class.
 * 
 * 3) NOTE: This class has a JUnit tester DataInfoTester which confirms that
 * the correct concrete subclasses are being found, and that the names
 * are being properly transformed using the given functions. This is useful 
 * as a benchmark as the project grows and changes. It also follows an
 * important component of the design process -- TESTING! Testing is important
 * when working in a group setting because others will begin to build their code 
 * around yours once you push it to the master branch. This is only acceptable 
 * if the code is thoroughly tested and will not produce new bugs.
 * 
 * LOOKING FORWARD: Given more time, I would want to find a way to transition this
 * to a Util class. It is slightly messy to instantiate a DataInfoGenerator
 * object (with no parameters) just to get access to its methods.
 * 
 * @author maddiebriere
 *
 */

public class DataInfoGenerator extends HierarchyInfoGenerator{
	
	//Keys for accessing class path property file (classpaths.properties)
	private static final String DATA = "Data";
	private static final String SUPER_DATA = "GenData";
	
	/**
	 * This constructor generates a HierarchyInfoGenerator using the Data
	 * composition package as the most concrete package (holding things like
	 * MoveWithSetPathdata) and the compositiongen package as the most abstract
	 * package (holding things like MoveData).
	 */
	public DataInfoGenerator() {
		super(ClassFinder.getClass(DATA), ClassFinder.getClass(SUPER_DATA));
	}
	
	/**
	 * Simplifies the given class by getting the simple class name
	 * and removing Data. 
	 * 
	 * Example:
	 * Input: Class<MoveWithSetPathData>
	 * Output: MoveWithSetPath
	 * 
	 * @param className Class to get simplfied name from
	 * @return Simplified name
	 */
	@Override
	public String simplifyName(Class<?> className) {
		return className.getSimpleName().replace(DATA, "");
	}
	
	/**
	 * Rebuild the String path given a name by adding on
	 * the package name, a delimeter ('.'), the given name,
	 * and the classifier ("Data").
	 * 
	 * @param name String name with which to rebuild
	 * @return Address corresponding to this simplified key
	 */
	@Override
	protected String rebuildAddress(String name){
		return ClassFinder.getClass(DATA) + "." + name + DATA;
	}
	

}
