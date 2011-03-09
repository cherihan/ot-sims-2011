package utilities;

import java.util.Date;



public class DateUtils {
	
	public static Integer getDateAsInteger(Date input) {
		return Integer.valueOf(String.valueOf(input.getTime()/1000));
	}

	public static Date getTimestampAsDate(long dateTimestamp) {
		Date dt = new Date(1000 * dateTimestamp);
		return dt;
	}

	public static String getIntervalAsText(int delta) {
		if(delta < 0) {
			return DateUtils.getIntervalAsTextWithPrefix(-delta, "il y a ");
		}else{
			return DateUtils.getIntervalAsTextWithPrefix(delta, "dans ");
		}
	}
	
	public static String getIntervalAsTextWithPrefix(int delta, String prefix) {
		if(delta < 60) {
			return prefix+delta+" sec";
		}else if(delta < (60*60)) {
			Integer subdelta = (int) Math.ceil(delta/60);
			return prefix+subdelta+" min";
		}else if(delta < (60*60*24)) {
			Integer subdelta = (int) Math.ceil(delta/(60*60));
			return prefix+subdelta+" hr";
		}else{
			Integer subdelta = (int) Math.ceil(delta/(60*60*24));
			return prefix+subdelta+" jour";
		}
	}

}
