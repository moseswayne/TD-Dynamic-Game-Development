package gameengine.grid.interfaces.ActorGrid;

/**
 * @author Gideon
 * 
 * Because we don;t know what kind of grid access rights each actor will need, I
 * encapsulated every possible grid type into the MasterGrid interface. This interface is intended
 * to be passed to the actors such that they can split it up into the grid type of interest
 * for their decision making classes. If any further additions were added to the grid interface, 
 * MasterGrid would have to extend them. 
 *
 */

public interface MasterGrid extends ReadShootMoveGrid, ReadAndDamageGrid{

}
