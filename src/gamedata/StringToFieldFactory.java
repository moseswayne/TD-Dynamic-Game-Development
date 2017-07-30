package gamedata;

import types.BasicActorType;

public class StringToFieldFactory {
	
	public static Object getObject(String s, Class c){
		
		if(c==Integer.class){
			return Integer.parseInt(s);
		}
		if(c==int.class){
			return (int)(Integer.parseInt(s));
		}
		if(c==Double.class){
			return Double.parseDouble(s);
		}
		if(c==double.class){
			return (double)Double.parseDouble(s);
		}
		if(c==BasicActorType.class){
			return new BasicActorType(s);
		}
		if(c==String.class){
			return s;
		}
		else return null;
	}
}
