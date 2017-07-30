package gamestatus;

public interface WriteableGameStatus {
	void addExperience(double exp);
	void addMoney(int mon);
	void spendMoney(int mon);
	void loseLife();
	void gainLife();
	void setMyEnemiesLeft(int enemiesLeft);
}
