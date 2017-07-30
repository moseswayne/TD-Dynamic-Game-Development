package gamedata.compositiongen;

/**
 * Adds money to the game at a set rate
 * 
 * @author maddiebriere
 *
 */

public class AddMoneyData extends ActData {
	private Double money;
	private Integer rate;
	
	public AddMoneyData(){
		this(0.0, 0);
	}
	
	public AddMoneyData(Double myMoney, Integer myRate){
		this.money = myMoney;
		this.rate = myRate;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double myMoney) {
		this.money = myMoney;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
	
	
	
}

