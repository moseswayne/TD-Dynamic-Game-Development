package gamestatus;

import ui.player.users.InitialGameStatus;
import ui.player.users.WriteableUser;
import util.observerobservable.VoogaObservableMap;

/**
 * An Observable map of Game Status indicators
 * @author sarahzhou
 *
 */
public class GameStatus extends VoogaObservableMap<String,String> implements WriteableGameStatus, ReadableGameStatus{

	private final String EXPERIENCE = "Experience";
	private final String MONEY = "Money";
	private final String LEVEL = "Level";
	private final String LIVES = "Lives";
	private final String ENEMIES_LEFT = "Enemies Left";
	
	private WriteableUser myWriteableUser;
	private InitialGameStatus myInitialGameStatus;
	
	private static int INIT_LEVEL = 1;
	private static double INIT_EXP = 0;
	
	public GameStatus(WriteableUser writeableUser,InitialGameStatus initialGameStatus) {
		myWriteableUser =writeableUser;
		myInitialGameStatus = initialGameStatus;
		setInitialValues();
	}
	
	public void setInitialValues() {
		setMyMoney(0);
		myInitialGameStatus.getInitMoney().ifPresent((money) -> setMyMoney(money));
		setMyLevel(INIT_LEVEL);
		setMyExperience(INIT_EXP);
		setMyLives(myInitialGameStatus.getInitLives());
	}

	public void addExperience(double exp){
		setMyExperience(getExp()+exp);
	}

	public void addMoney(int mon){
		setMyMoney(getMoney()+mon);
	}

	public void spendMoney(int mon){
		setMyMoney(getMoney()-mon);
	}

	public void levelUp() {
		int newLevel = getLevel()+1;
		setMyLevel(newLevel);
	}

	public void setMyExperience(double myExperience) {
		myWriteableUser.setExperience(myExperience);
		myMap.put(EXPERIENCE, Double.toString(myExperience));
		notifyObservers();
	}

	public void setMyMoney(int myMoney) {
		myMap.put(MONEY, Integer.toString(myMoney));
		notifyObservers();
	}

	public void setMyLevel(int myLevel) {
		myMap.put(LEVEL, Integer.toString(myLevel));
		notifyObservers();
	}
	
	@Override
	public void loseLife() {
		setMyLives(getLives()-1);
	}

	@Override
	public void gainLife() {
		setMyLives(getLives()+1);
	}
	
	public void setMyLives(int lives) {
		myMap.put(LIVES, Integer.toString(lives));
		notifyObservers();
	}

	@Override
	public double getExp() {
		return Double.parseDouble(myMap.get(EXPERIENCE));
	}

	@Override
	public int getMoney() {
		return Integer.parseInt(myMap.get(MONEY));
	}

	@Override
	public int getLevel() {
		return Integer.parseInt(myMap.get(LEVEL));
	}

	@Override
	public int getLives() {
		return Integer.parseInt(myMap.get(LIVES));
	}

	public int getEnemiesLeft() {
		return Integer.parseInt(myMap.get(ENEMIES_LEFT));
	}
	
	public void enemyEliminated() {
		setMyEnemiesLeft(getEnemiesLeft() -1);
	}

	public void setMyEnemiesLeft(int enemiesLeft) {
		myMap.put(ENEMIES_LEFT, Integer.toString(enemiesLeft));
		notifyObservers();
	}
	
	
	
}
