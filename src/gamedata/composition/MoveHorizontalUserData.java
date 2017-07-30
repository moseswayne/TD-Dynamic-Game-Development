package gamedata.composition;

import gamedata.compositiongen.MoveAxisUserData;

/**
 * Allows the user to move this actor horizontally .
 * 
 * @author maddiebriere
 *
 */

public class MoveHorizontalUserData extends MoveAxisUserData {
	public MoveHorizontalUserData(){
		super();
	}
	
	public MoveHorizontalUserData(String posButton, String negButton, Integer mySensitivity){
		super(posButton, negButton, mySensitivity);
	}
}
