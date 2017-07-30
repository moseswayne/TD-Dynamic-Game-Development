package gamedata;

/**
 * Defines information about a single enemy in a single wave, 
 * including the actor and its number in this wave
 * 
 * @author maddiebriere
 *
 */

public class EnemyInWaveData {
	
	//Corresponds to a number in the Map.
	private ActorData myActor;
	private int myWaveNumber;
	
	public EnemyInWaveData(ActorData myActor, int myNumber) {
		this.myActor = myActor;
		this.myWaveNumber = myNumber;
	}

	public ActorData getMyActor() {
		return myActor;
	}

	public void setMyActor(ActorData myActor) {
		this.myActor = myActor;
	}


	public int getOption() {
		return myWaveNumber;
	}
	
	public void setMyNumber(int myNumber) {
		this.myWaveNumber = myNumber;
	}


	public String toString(){
		//TODO: NEED?
		return null;
		//return  "ActorData:" +myData.toString()+" Number: "+myNumber+"  Paths:  "+myPaths.toString();
	}
}
