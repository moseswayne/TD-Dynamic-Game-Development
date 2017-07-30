package gamedata.composition;

import gamedata.compositiongen.MoveAxisUserData;

/**
 * Allows the user to move this actor vertically .
 * 
 * @author maddiebriere
 */

public class MoveVerticalUserData extends MoveAxisUserData {
	
	public MoveVerticalUserData(){
		super();
	}
	
	public MoveVerticalUserData(String posButton, String negButton, Integer mySensitivity){
		super(posButton, negButton, mySensitivity);
	}
}
