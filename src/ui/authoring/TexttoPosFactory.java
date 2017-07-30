package ui.authoring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javafx.geometry.Orientation;
import javafx.scene.layout.BorderPane;
import ui.authoring.display.DisplayMenu;


public class TexttoPosFactory {
	public static final Map<String,Orientation>ORIENTATION_MAP=getOrientationMap();
	static Map<String,Orientation> getOrientationMap(){
		HashMap<String,Orientation>map=new HashMap<String,Orientation>();
		map.put("Top",Orientation.HORIZONTAL );
		map.put("Buttom",Orientation.HORIZONTAL );
		map.put("Right",Orientation.VERTICAL );
		map.put("Left",Orientation.VERTICAL );
		return map;
	}
public static void updateMenuPosition(BorderPane pane, String s,DisplayMenu menu){
	Method[]methods=pane.getClass().getMethods();
	resetCardinalDirections(pane);
	for(Method method:methods){
		if(method.getName().equals(getString(s))){
			method.setAccessible(true);
			 try {
				System.out.println(method.getParameterTypes()[0].getName());
				
				Object o = method.invoke(pane,menu.getNode());
				
					menu.getNode().setOrientation(ORIENTATION_MAP.get(s));
				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				//
				e.printStackTrace();
			}
		}
	}
}
private static void resetCardinalDirections(BorderPane pane){
	//TODO:fix this shitty code
	pane.setRight(null);
	pane.setLeft(null);
	pane.setTop(null);
	pane.setBottom(null);
}
private static String getString(String s){
	return String.format("set%s", s);
}
}
