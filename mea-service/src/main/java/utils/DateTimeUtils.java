package utils;

import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

public class DateTimeUtils {
	public static String  DEFAULT_FORMAT ="yyyyMMdd HHmmss";
	
	public static String format(Date date,String format){
		if(null == date || format ==null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
}
