package factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * UPDATES:
 * 1) New extensions of this class were created for this project, implementing 
 * new elements of the component
 * 2) Future goals include use/incorporation of VoogaSalad Reflection Util
 * This abstract class is used throughout the project to perform reflection and
 * build an object given the path name desired.
 * 
 * There are several neat consequences of this class:
 * 
 * 1) This class takes a generic type, meaning that any type of
 * object can be created from a subclass of this class. Any form of reflection
 * used in this project could be built from this basic platform (saves a lot of
 * typing!)
 * 
 * 2) With the abstract failResponse() method, any factory class must implement
 * a response to failure. This flexibility allows for some failures (such as in
 * instruction creation) to throw errors and alert the user of a problem, and
 * others to catch the problem without alerting the user (e.g., when trying to
 * create a BuilderUtil object, if the command type is not recognized as one of
 * the BuilderUtil subclasses, the factory just returns null instead, to signal
 * use of the default construction method).
 * 
 * 3) The error-handling is taken care of at a high-level, letting subclasses
 * ignore this responsibility.
 *
 * 
 * @author maddiebriere
 *
 */

public abstract class AbstractFactory<A> {
	private String path;

	/**
	 * Constructor for factory using object path
	 * 
	 * @param path
	 *            The path to the folder in which these object types reside. For
	 *            BuilderUti, this path will be "interpreter.builders."
	 */
	public AbstractFactory(String path) {
		this.path = path;
	}

	/**
	 * Make an object of type A with the name 'name' (e.g., GroupStart) and the
	 * arguments 'args' for the constructor
	 * 
	 * @param name
	 *            Name of the object being created
	 * @param args
	 *            Arguments to pass to the constructor
	 * @return Object of type A
	 */
	public A make(String name, Object... args) {
		return buildObject(generateClassPath(name), args);
	}
	
	/**
	 * Make an object of type A with the name 'name' (e.g., GroupStart) and the
	 * arguments 'args' for the constructor
	 * 
	 * @param name
	 *            Name of the object being created
	 * @param args
	 *            Arguments to pass to the constructor
	 * @return Object of type A
	 */
	public A make(Object... args) {
		return buildObject(generateClassPath(""), args);
	}

	/**
	 * Generate the full class path to the object type
	 * 
	 * @param name
	 *            The name of the object being created
	 * @return A String representing the class path
	 */
	private String generateClassPath(String name) {
		return path + generateObjectType(name);
		
	}

	/**
	 * Generate the object type for this factory. For instance, GroupStart -->
	 * GroupStartUtil for BuilderUtil
	 * 
	 * @param name
	 *            The "shorthand" name of the initial object
	 * @return The extended name of the expected object
	 */
	protected abstract String generateObjectType(String name);

	/**
	 * The object A returned/ response of the class if the reflection does not
	 * work. A sample response could be to throw a reflection error.
	 * 
	 * @return Default object of type A
	 */
	protected abstract A failResponse();

	/**
	 * The core of the reflection. This takes in specification for an object of
	 * type A and created that object (or responds as specified by failResponse)
	 * 
	 * @param classPath
	 *            The full address to the object type
	 * @param args
	 *            The arguments to pass to the constructor
	 * @return An object of type A
	 */
	private A buildObject(String classPath, Object... args) {
		Class<?> clazz;
		try {
			clazz = Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			return failResponse();
		}
		return tryToBuild(clazz, args);
	}

	/**
	 * Actual construction of object given a class type (reflection has already
	 * occurred).
	 * 
	 * @param clazz
	 *            The class type
	 * @param args
	 *            The arguments for the constructor
	 * @return An object of type A
	 */
	@SuppressWarnings("unchecked")
	private A tryToBuild(Class<?> clazz, Object... args) {
		A toRet = null;
		Class<?>[] classes = getClasses(args);
		try {

			Constructor<?> ctor;
			if(args.length!=0){
				ctor= clazz.getDeclaredConstructor(classes);
			}else{
				ctor= clazz.getDeclaredConstructor();
			}

			toRet = (A) ctor.newInstance(args);

		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			throwError();
		}
		return toRet;
	}

	/**
	 * Throw a basic reflection error
	 */
	protected void throwError() {
		//throw new Exception(RESOURCE_REFLECTION_NAME);
	}

	/**
	 * Generate a Class [] corresponding to the Object [] args
	 * 
	 * @param args
	 *            The objects to convert to Class types
	 * @return A Class [] corresponding to the input objects
	 */
	protected Class<?>[] getClasses(Object... args) {
		return Arrays.asList(args).stream().map(p -> p.getClass()).toArray(Class[]::new);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
