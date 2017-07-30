package gamedata;

import java.util.ArrayList;
import java.util.List;


/**
 * Information about enemies in a single wave.
 * 
 * Wave ex: 
 * - Enemy Type: 1 Number: 200 PathOptions: 1, 2
 * 
 * Number of troops and its types and its path options as indexes 
 *  
 * @author anh
 * @author maddiebriere
 *
 */
public class WaveData {
	List<EnemyInWaveData> waveEnemies; 
	
	public WaveData(){
		waveEnemies = new ArrayList<EnemyInWaveData>(); 
	}
	
	/**
	 * Remove actor if it exists in any of the EnemyInWaveDatas
	 * 
	 * @param actor ActorData to remove
	 */
	public void removeActor(ActorData actor){
		if(!contains(actor)){
			return;
		}
		for(EnemyInWaveData enemy: waveEnemies){
			if(enemy.getMyActor().equals(actor)){
				waveEnemies.remove(enemy);
			}
		}
	}
	
	public boolean contains(ActorData actor){
		for(EnemyInWaveData enemy: waveEnemies){
			if(enemy.getMyActor().equals(actor)){
				return true;
			}
		}
		return false;
	}
	
	public void setQuantity(ActorData actor, int quantity){
		for(EnemyInWaveData enemy: waveEnemies){
			if(enemy.getMyActor().equals(actor)){
				enemy.setMyNumber(quantity);
			}
		}
	}
	
	public int getQuantity(ActorData actor){
		int toRet = 0;
		for(EnemyInWaveData enemy: waveEnemies){
			if(enemy.getMyActor().equals(actor)){
				toRet = enemy.getOption();
			}
		}
		return toRet;
	}
	
	public void addWaveEnemy(EnemyInWaveData enemy){
		waveEnemies.add(enemy); 
	}
	
	public List<EnemyInWaveData> getWaveEnemies(){
		return waveEnemies;
	}
	
	public String toString(){
		return waveEnemies.toString();
	}
	
}
