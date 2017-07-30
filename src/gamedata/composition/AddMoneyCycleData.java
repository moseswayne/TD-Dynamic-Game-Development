package gamedata.composition;

import gamedata.compositiongen.AddMoneyData;

/**
 * This data object represents a property that earns you cash! 
 * Set the cycle and the resulting property lets your actor generate $$$.
 * 
 * @author maddiebriere
 *
 */

public class AddMoneyCycleData extends AddMoneyData  {
	private Integer frequency;

	public AddMoneyCycleData(){
		this(0, 0.0, 0);
	}
	
	public AddMoneyCycleData(Integer myFrequency, Double myMoney, Integer myRate) {
		super(myMoney, myRate);
		this.frequency = myFrequency;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	

	
	
	
}
