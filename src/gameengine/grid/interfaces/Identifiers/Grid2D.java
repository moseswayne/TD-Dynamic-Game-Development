package gameengine.grid.interfaces.Identifiers;

import com.sun.javafx.geom.Point2D;

/**
 * 
 * @author Gideon
 *
 * This interface is intended to mirror javaFX's {@link Point2D} class
 * in the sense that it it used to hold an x and a y coordinate. We implemented our own
 * interface instead of using Java's because it will allow for 
 * more manageable XML generation. This interface hold only getters for location such that when passed
 * to an actor to read, they cannot change the location, only read it. 
 */
public interface Grid2D {
	
	/**
	 * @return the x coordinate associated with the Grid2D instance
	 */
	double getX();
	
	/**
	 * @return the y coordinate associated with the Grid2D instance
	 */
	double getY();

}
