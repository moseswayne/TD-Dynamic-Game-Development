package gamedata.backend_generated_data;

import java.util.List;

import gamedata.compositiongen.Data;
import gamedata.compositiongen.MoveData;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import util.PathUtil;

/**
 * Data class for MoveWithDestination Property, which
 * is used in Projectiles to define a limited type of movement
 * 
 * @author maddiebriere
 *
 */

public class MoveWithDestinationData extends MoveData{
	private double myStartX;
	private double myStartY;
	private double myFinalX;
	private double myFinalY;
	

	
	/**
	 * speed comes from ProjectileType.getSpeed()
	 */
public MoveWithDestinationData(Double mySpeed, Double startX,
		Double startY, Double finalX, 
		Double finalY){

		super(mySpeed);
		myStartX = startX;
		myStartY = startY;
		myFinalX = finalX;
		myFinalY = finalY;
	}
	
	public List<Grid2D> getStraightPath(){
		return PathUtil.getIncrementPoints(new Coordinates(myStartX, myStartY), 
				new Coordinates(myFinalX, myFinalY), 
				getMySpeed());
	}

	public Coordinates getInitialLocation() {
		return new Coordinates(myStartX, myStartY);
	}

	public void setInitialLocation(Coordinates initialLocation) {
		myStartX = initialLocation.getX();
		myStartY = initialLocation.getY();
	}

	public Coordinates getFinalLocation() {
		return new Coordinates(myFinalX, myFinalY);
	}

	public void setFinalLocation(Coordinates finalLocation) {
		myFinalX = finalLocation.getX();
		myFinalY = finalLocation.getY();
	}
	
	
	
}
