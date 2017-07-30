package builders.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gamedata.FieldData;

/**
 * Return information about the fields from
 * a class. 
 * 
 * NOTE: Similar methods can be found in the Reflection Util
 * in the VoogaSalad Util package. Future goals include 
 * incorporation of this util into this class.
 * 
 * @author maddiebriere
 *
 */
public class FieldGenerator{

	public static List<Field> getFields(String className)
			throws ClassNotFoundException {
		return getFields(Class.forName(className));
	}
	
	public static List<Field> getFields(Class<?> clzz){
		ArrayList<Field> addList = new ArrayList<Field>();
		while(!clzz.equals(Object.class)){
			addList.addAll(Arrays.asList(clzz.getDeclaredFields()));
			clzz = clzz.getSuperclass();
		}
		return addList;
	}
	
	/**
	 * Return the field names in the data objects mapped to their
	 * current values
	 * 
	 * @param data Data object to check
	 * @return Mapping of field names to values
	 */
	public static <T extends Object> Map<String, Object>  getFields(T obj){
		Map<String, Object> fieldMap = new LinkedHashMap<String,Object>();
		List<Field> fields = getFields(obj.getClass());
		
		for(Field f: fields){
			try {
				f.setAccessible(true);
				fieldMap.put(f.getName(), f.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fieldMap;
	}
	
	/**
	 * Return information above all of the fields 
	 */
	public static List<FieldData> getFieldDatas(List<Field> fields){
		List<FieldData> fieldDatas = new ArrayList<FieldData>();
		fields.stream().forEach(p -> {
			fieldDatas.add(new FieldData(p.getName(), p.getType()));
		});
		return fieldDatas;
	}
}
