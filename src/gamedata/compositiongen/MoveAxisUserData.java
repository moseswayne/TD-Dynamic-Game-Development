package gamedata.compositiongen;

/**
 * Defines subclass of ActData objects that allow for movement
 * of the actor by the user, in various directions and via
 * various means. 
 * 
 * @author maddiebriere
 *
 */

public class MoveAxisUserData extends ActData {
	private String posButton;
	private String negButton;
	private Integer mySensitivity;
	
	
	public MoveAxisUserData(){
		this("", "", 0);
	}
	
	public MoveAxisUserData(String posButton, String negButton, Integer mySensitivity) {
		super();
		this.posButton = posButton;
		this.negButton = negButton;
		this.mySensitivity = mySensitivity;
	}
	public String getPosButton() {
		return posButton;
	}
	public void setPosButton(String posButton) {
		this.posButton = posButton;
	}
	public String getNegButton() {
		return negButton;
	}
	public void setNegButton(String negButton) {
		this.negButton = negButton;
	}
	public Integer getMySensitivity() {
		return mySensitivity;
	}
	public void setMySensitivity(Integer mySensitivity) {
		this.mySensitivity = mySensitivity;
	}
	
	
}
