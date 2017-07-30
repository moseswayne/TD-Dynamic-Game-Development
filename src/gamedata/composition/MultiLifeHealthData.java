package gamedata.composition;

import gamedata.composition.LimitedHealthData;

/**
 * Multiple life health data --
 * set the number of lives
 * 
 * @author maddiebriere
 *
 */

public class MultiLifeHealthData extends LimitedHealthData{
	public Integer myLives;
	
	public MultiLifeHealthData(){
		this(0, 0.0);
	}
	
	public MultiLifeHealthData(Integer lives, Double health){
		super(health);
		myLives = lives;
	}

	public Integer getLives() {
		return myLives;
	}

	public void setMyLives(Integer myLives) {
		this.myLives = myLives;
	}
	
}
