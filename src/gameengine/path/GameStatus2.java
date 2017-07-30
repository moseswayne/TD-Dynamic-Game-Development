package gameengine.path;

import util.observerobservable.VoogaObservableMap;

/**
 * @author sarahzhou
 * Observable Map of Game Status elements for front end to display (Experience, money, level)
 *
 */
public class GameStatus2 extends VoogaObservableMap<String,String>{

	private final String EXPERIENCE = "Experience";
	private final String MONEY = "Money";
	private final String LEVEL = "Level";

	public void addExperience(double exp){
		double newExp = Double.parseDouble(myMap.get(EXPERIENCE))+exp;
		setMyExperience(newExp);
	}

	public void addMoney(double mon){
		double newMon = Double.parseDouble(myMap.get(MONEY))+mon;
		setMyMoney(newMon);
	}

	public void spendMoney(double mon){
		addMoney(-mon);
	}

	public void levelUp() {
		int newLevel = Integer.parseInt(myMap.get(LEVEL))+1;
		setMyLevel(newLevel);
	}

	public void setMyExperience(double myExperience) {
		myMap.put(EXPERIENCE, Double.toString(myExperience));
		notifyObservers();
	}

	public void setMyMoney(double myMoney) {
		myMap.put(MONEY, Double.toString(myMoney));
		notifyObservers();
	}

	public void setMyLevel(double myLevel) {
		myMap.put(LEVEL, Double.toString(myLevel));
		notifyObservers();
	}

	public void clear() {
		myMap.clear();
		notifyObservers();
	}
}
