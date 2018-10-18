package com.mea.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


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
