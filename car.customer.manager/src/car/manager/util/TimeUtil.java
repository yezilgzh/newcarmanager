package car.manager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static String getStandardDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return df2.format(date);
	}

}
