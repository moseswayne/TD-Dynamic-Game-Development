package gameengine.grid.interfaces.ActorGrid;

/**
 * 
 * @author Gideon
 *
 * This interface is designed for actor properties that need to both move as well as spawn
 * other actors. For more information on the SpawnableGrid and the MovableGrid
 * {@link ReadAndSpawnGrid} and {@link ReadAndMoveGrid} repectively
 */
public interface ReadShootMoveGrid extends ReadAndSpawnGrid, ReadAndMoveGrid{

}
