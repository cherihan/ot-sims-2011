package utilities;

import java.util.Calendar;
import java.util.Date;



public class DateUtils {
	
	public static Integer getDateAsInteger(Date input) {
		return Integer.valueOf(String.valueOf(input.getTime()/1000));
	}

	public static Date getTimestampAsDate(Integer dateTimestamp) {
		Date dt = new Date(Long.valueOf(1000 * dateTimestamp));
		return dt;
	}
	
	public static Date now() {
	    Calendar cal = Calendar.getInstance();
	    return cal.getTime();
	 }

}
