package com.mea.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Properties {
	
	Path POSTIVE_PATH = Paths.get("lib/positive.txt");
	Path NEGATIVE_PATH = Paths.get("lib/negative.txt");
	Path DEFUALT_PRIMITIVE_DATA_PATH = Paths.get("src/com.mea.resource/primitiveData.xml");
	Path DEFUALT_PRIMITIVE_DATA_PATH_120 = Paths.get("src/com.mea.resource/SMSData120.xml");
	Path DEFUALT_PARTICIPLE_RESULT_PATH = Paths.get("src/com.mea.resource/participleResult.txt");
	String DEFUALT_CHARSET ="UTF-8";
	Integer PARTICIPLE_SIZE = 20;
	Integer TAIL_ORING_NO =0;
	Integer TAIL_ORING_K_MEANS = 1;
	Integer TAIL_ORING_K_MEDOIDS = 2;
	String DEFUALT_OUT_PUT_NAME ="outPut.txt";
	
	
	Map<String,Double> FREQUENCY_VALUE_MAP = new HashMap<String,Double>() {

		private static final long serialVersionUID = -1383652466908690738L;
		{
			put("less than 1 sms daily", 1d);
			put("1 to 2 sms daily", 2d);
			put("2 to 5 sms daily", 3d);
			put("5 to 10 sms daily", 4d);
			put("more than 10 sms daily", 5d);
			put("more than 50 sms daily", 6d);
			put("unknown", 7d);
		}
	};
	
	ArrayList<String> RELATIONSHIP = new ArrayList<String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("Relatives");
			add("Friend");
			add("Classmate");
			add("Colleague");
			add("Stranger");
			add("System");
		}
	};
	
	interface ApiParam{
		String LTP_URL ="http://com.mea.api.ltp-cloud.com/analysis/?";
		String API_KEY ="I1S0W116l3JGOkLCtd9fYFkAqBGTIEiYSV2YfHCP";
		String PATTERN_WS ="ws";
		String FORMAT_PLAIN = "plain";
	}
	
	interface Separator{
		String BLANK =" ";
		String HASHTAG = "#";
		String COMMA = ",";
		String MIDDLE_LINE = "-";
		String TAB = "\t";
		String LINE_FEED ="\r\n";
	}
	
	interface TestPath {
			Path SAMPLE_TYPE_PATH = Paths.get("C:\\Users\\dell\\Desktop\\xc\\smapleType100.txt");
			Path TEST_TYPE_PATH = Paths.get("C:\\Users\\dell\\Desktop\\xc\\testType100.txt");
			Path TEST_DATA_PATH = Paths.get("src/com.mea.resource/ToTestSMSData.xml");
			Path saveResult_path =Paths.get("C:\\Users\\dell\\Desktop\\xc\\a.txt"); 
	}
	
	
	
	
}
