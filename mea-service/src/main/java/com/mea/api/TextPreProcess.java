package com.mea.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.mea.config.Properties;

public class TextPreProcess {
	
//	public static void main(String[] args) {
//		String text    = "1 老師,媽咪話想買盒月餅比你,你要傳統定冰皮?";
//		try {
//			System.out.println(participleSentence(text));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String com.mea.test ="";
//		System.out.println(com.mea.test.trim());
//
//
//	}
	
    public static String participleSentence(String context) throws IOException {
        String api_key = Properties.ApiParam.API_KEY;
        String pattern =  Properties.ApiParam.PATTERN_WS;
        String format  =  Properties.ApiParam.FORMAT_PLAIN;
//        String text    = "老師,媽咪話想買盒月餅比你,你要傳統定冰皮? #2 我想试试有没有问题";
        String text = URLEncoder.encode(context, Properties.DEFUALT_CHARSET);

        URL url     = new URL(Properties.ApiParam.LTP_URL
                              + "api_key=" + api_key + "&"
                              + "text="    + text    + "&"
                              + "format="  + format  + "&"
                              + "pattern=" + pattern);
//        URL url = new URL("https://www.baidu.com");
        URLConnection conn = url.openConnection();
        conn.connect();
        
        BufferedReader innet = new BufferedReader(new InputStreamReader(
                conn.getInputStream(),
                Properties.DEFUALT_CHARSET));
        
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = innet.readLine())!= null) {
        	result.append(line);
        }
        innet.close();
        
        return result.toString();
    }
}
