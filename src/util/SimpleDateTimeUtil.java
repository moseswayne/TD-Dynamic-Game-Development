/**
 * 
 */
package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author harirajan
 *
 */
public class SimpleDateTimeUtil {
	
	private static final Date DATE = new Date();
	
	private DateFormat dateFormat;
	
	public String getDateNums() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return get();
	}
	
	public String getDateWritten(boolean abbreviated) {
		dateFormat = (abbreviated) ? new SimpleDateFormat("MMM d, yyyy") : new SimpleDateFormat("MMMM d, yyy");
		return get();
	}
	
	public String getDateTimeWritten(boolean abbreviated, boolean timezone) {
		return getDateWritten(abbreviated) + " " + getTime(timezone);
	}
	
	public String getDateTimeNums(boolean timezone) {
		return getDateNums() + " " + getTime(timezone);
	}
	
	public String getTime(boolean timezone) {
		dateFormat = (timezone) ? new SimpleDateFormat("hh:mm aaa z") : new SimpleDateFormat("hh:mm aaa"); 
		return get();
	}
	
	private String get() {
		return dateFormat.format(DATE);
	}

}
