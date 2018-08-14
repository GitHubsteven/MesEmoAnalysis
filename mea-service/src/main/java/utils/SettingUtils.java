package utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import config.Properties;
import domain.TypeBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rongbin.xie on 2017/7/13.
 */
public class SettingUtils {
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		TypeBean type1 =new TypeBean();
		type1.setId("1");
		type1.setType("friends");
		String typeJson = gson.toJson(type1);
		System.out.println(typeJson);
		
		TypeBean type2= gson.fromJson(typeJson, TypeBean.class);
		System.out.println(type2);
	}

	/**
	 * 通过文件获取词汇配置
	 * 
	 * @return List<String>
	 */
	public static List<String> getWordsFromFile(Path filePath, String regex) throws IOException {

		String context = FileUtils.readFileToString(filePath.toFile(), Properties.DEFUALT_CHARSET);
		if (null == context || "".equals(context)) {
			return null;
		}
		String[] sentences = context.split(regex);
		return Arrays.asList(sentences);
	}

	/**
	 * 输出文本到指定的路径下
	 * 
	 * @param text
	 *            文本内容
	 * @param targetPath
	 *            名称路径
	 */
	@SuppressWarnings("deprecation")
	public static Boolean outPutTextToFile(String text, Path targetPath) {
		try {
			FileUtils.writeStringToFile(targetPath.toFile(), text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static List<TypeBean> getTypeByPath(Path dataPath) {
		String regex = "\\s+";
		List<String> types = new ArrayList<String>();
		List<TypeBean> typeList = new ArrayList<TypeBean>();
		try {
			types = SettingUtils.getWordsFromFile(dataPath, regex);
			if (CollectionUtils.isNotEmpty(types)) {
				for (int index = 0; index < types.size(); index++) {
					TypeBean bean = new TypeBean();
					bean.setId(String.valueOf(index + 1)).setType(types.get(index));
					typeList.add(bean);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeList;
	}

	public static Double doubleDivide(Double dividor, Double divide) {
		if (divide == null) {
			return null;
		} else if (divide == 0d && dividor == null) {
			return null;
		} else {
			return dividor / divide;
		}
	}

	public static Double doubleDivide(Integer dividor, Integer divide) {
		if (divide == null) {
			return null;
		} else if (divide == 0 && dividor == null) {
			return null;
		}else if (divide == 0 && dividor == 0) {
			return 0d;
		} else {
			return Double.valueOf(dividor) / Double.valueOf(divide);
		}
	}
}
