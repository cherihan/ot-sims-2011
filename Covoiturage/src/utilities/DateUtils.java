package utilities;

import java.util.Date;



public class DateUtils {
	
	public static Integer getDateAsInteger(Date input) {
		return Integer.valueOf(String.valueOf(input.getTime()/1000));
	}
}
