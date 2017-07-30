package ui.authoring.actor;

import gamedata.compositiongen.Data;
import javafx.scene.control.Label;
import ui.general.CustomColors;

/**
 * Helper class for organizational components of
 * Actor Editor like:
 * --> Varied colors by types
 * --> Restrictions on removal/addition
 * @author maddiebriere
 *
 */

public class UIActorUtil {

	public static void colorByType(String className, Label name){
		if(isHealth(className)){
			name.setTextFill(CustomColors.AMBER_700);
		}else{
			name.setTextFill(CustomColors.BLUE_800);
		}
	}
	
	public static boolean isHealth(String className){
		return className.contains("Health");
	}
	
	public static boolean isHealth(Data data){
		return isHealth(data.getClass().getSimpleName());
	}
	
	public static boolean canRemoveData(Data data){
		return !isHealth(data.getClass().getSimpleName());
	}
	
	public static boolean canAddData(Data data){
		return !isHealth(data.getClass().getSimpleName());
	}
	
}
