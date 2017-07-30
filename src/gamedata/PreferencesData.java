package gamedata;

import java.util.Optional;

/**
 * Preferences set in authoring stage.
 * Includes the number of lives a player
 * is allowed and several boolean for settings 
 * like:
 * 1) Do the enemies loop back around
 * 2) Can you attack the towers
 * 3) Do you want money 
 * 4) Do you want experience
 * 5) Are there pauses between waves
 * 6) Should actors be cleared with each level
 * 
 * @author maddiebriere
 *
 */

public class PreferencesData{
	static final int NUM_LIVES = 3;
	static final boolean DEFAULT = false;
	
	private Optional<Integer> numLives;
	private Optional<Integer> startMoney;
	private Optional<Boolean> enemyLoop;
	private Optional<Boolean> towersAttackable;
	private Optional<Boolean> wantMoney;
	private Optional<Boolean> expByLevel;
	private Optional<Boolean> pauseBetweenWaves;
	private Optional<Boolean> cleanLevel;
	
	private String musicFilePath = "music/hero_song.mp3";
	
	public PreferencesData(){
		this(Optional.of(0), Optional.of(false), Optional.of(false), Optional.of(false),
				Optional.of(false), Optional.of(false), Optional.of(false));
	}
	
	public PreferencesData(Optional<Integer> numLives, Optional<Boolean> enemyLoop, Optional<Boolean> towersAttackable,
			Optional<Boolean> wantMoney, Optional<Boolean> expByLevel, Optional<Boolean> pauseBetweenWaves,
			Optional<Boolean> cleanLevel) {
		super();
		this.numLives = numLives;
		this.enemyLoop = enemyLoop;
		this.towersAttackable = towersAttackable;
		this.wantMoney = wantMoney;
		this.expByLevel = expByLevel;
		this.pauseBetweenWaves = pauseBetweenWaves;
		this.cleanLevel = cleanLevel;
		this.startMoney = Optional.of(0);
	}

	public int getNumLives(){
		return numLives.orElse(NUM_LIVES);
	}
	
	public boolean doEnemiesLoop(){
		return enemyLoop.orElse(DEFAULT);
	}
	
	public boolean areTowersAttackable(){
		return towersAttackable.orElse(DEFAULT);
	}
	
	public boolean wantMoney(){
		return wantMoney.orElse(DEFAULT);
	}
	
	public boolean expByLevel(){
		return expByLevel.orElse(DEFAULT);
	}
	
	public boolean pauseBetweenWaves(){
		//return pauseBetweenWaves.orElse(DEFAULT);
		return false;
	}
	
	public boolean cleanLevel(){
		//return cleanLevel.orElse(DEFAULT);
		return false;
	}

	public void setNumLives(Optional<Integer> numLives) {
		this.numLives = numLives;
	}

	public void setEnemyLoop(Optional<Boolean> enemyLoop) {
		this.enemyLoop = enemyLoop;
	}

	public void setTowersAttackable(Optional<Boolean> towersAttackable) {
		this.towersAttackable = towersAttackable;
	}

	public void setWantMoney(Optional<Boolean> wantMoney) {
		this.wantMoney = wantMoney;
	}

	public void setExpByLevel(Optional<Boolean> expByLevel) {
		this.expByLevel = expByLevel;
	}

	public void setPauseBetweenWaves(Optional<Boolean> pauseBetweenWaves) {
		this.pauseBetweenWaves = pauseBetweenWaves;
	}

	public Optional<Boolean> getCleanLevel() {
		return cleanLevel;
	}

	public void setCleanLevel(Optional<Boolean> cleanLevel) {
		this.cleanLevel = cleanLevel;
	}

	public Optional<Boolean> getEnemyLoop() {
		return enemyLoop;
	}

	public Optional<Boolean> getTowersAttackable() {
		return towersAttackable;
	}

	public Optional<Boolean> getWantMoney() {
		return wantMoney;
	}

	public Optional<Boolean> getExpByLevel() {
		return expByLevel;
	}

	public Optional<Boolean> getPauseBetweenWaves() {
		return pauseBetweenWaves;
	}

	public Optional<Integer> getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(Optional<Integer> startMoney) {
		this.startMoney = startMoney;
	}

	public String getMusicFilePath() {
		return musicFilePath;
	}

	public void setMusicFilePath(String musicFilePath) {
		this.musicFilePath = musicFilePath;
	}
	
	
	
	

	
	//TODO: Generate more preferences
	
	
}
