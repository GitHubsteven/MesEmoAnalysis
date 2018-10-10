package service;

import config.Properties;
import domain.BuildModelParameter;
import domain.MessageBean;
import domain.PRFBean;
import domain.TypeBean;
import org.apache.commons.collections4.CollectionUtils;
import utils.KNN;
import utils.SettingUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * 这个可能暂时不会用，为了赶时间，如果时间允许的话，我将会实现
 * 
 * @author dell
 *
 */
public class KNNModelAnaFactory {
	
	public static String buildModel(IBuildMessageService buildDataService, BuildModelParameter param, IBuilderModelHandler builderModelHandler){
		List<MessageBean> sampleMessages = builderModelHandler.getSampleMessages(param.getSamleContextPath());
		int size = sampleMessages.size();
		int sampleSize = size/2;
		
		List<MessageBean> newSampleMessages = sampleMessages.subList(0, sampleSize);
		
		List<TypeBean> sampleType = builderModelHandler.getSampleTypes(param.getSampleTypePath());
		
		Integer k = param.getK();
		
		List<MessageBean> testMessages = sampleMessages.subList(sampleSize, size);
		if (CollectionUtils.isEmpty(testMessages)) {
			return null;
		}
		testMessages.forEach(testMes -> {
			String type = KNN.knnCal(k, testMes, sampleMessages);
			testMes.setType(type);
		});
		// 计算每个类型的查准率和查全率和f值
		// List<PRFBean> rpfBeanList = new ArrayList<PRFBean>();

		Map<String, List<MessageBean>> mapForTestMes = testMessages.stream()
				.collect(Collectors.groupingBy(MessageBean::getType));

		List<TypeBean> testMesTypeList = buildDataService.getTypes(param.getTestTypePath());
		Map<String, List<TypeBean>> mapForTestType = new HashMap<String, List<TypeBean>>();
		if (CollectionUtils.isNotEmpty(testMesTypeList)) {
			mapForTestType = testMesTypeList.stream().collect(Collectors.groupingBy(TypeBean::getType));
		}
		StringBuilder result = new StringBuilder();
		result.append("RelationShip").append(Properties.Separator.TAB).append("Pression")
				.append(Properties.Separator.TAB).append("Recall").append(Properties.Separator.TAB).append("F1 Measure")
				.append(Properties.Separator.LINE_FEED);

		for (String type : Properties.RELATIONSHIP) {
			List<MessageBean> testMesList = mapForTestMes.get(type);
			List<TypeBean> realTestTypeList = mapForTestType.get(type);
			PRFBean prf = new PRFBean();
			Integer outputQty = 0;
			Integer actualQty = 0;
			Integer matchQty = 0;

			if (CollectionUtils.isNotEmpty(testMesList)) {
				outputQty = testMesList.size();
			}
			if (CollectionUtils.isNotEmpty(realTestTypeList)) {
				actualQty = realTestTypeList.size();
			}
			if (CollectionUtils.isNotEmpty(testMesList) && CollectionUtils.isNotEmpty(realTestTypeList)) {
				List<String> actualTypeIds = realTestTypeList.stream().map(TypeBean::getId)
						.collect(Collectors.toList());
				List<MessageBean> matchMes = testMesList.stream().filter(bean -> actualTypeIds.contains(bean.getId()))
						.collect(Collectors.toList());
				matchQty = matchMes.size();
			}
			prf.setType(type).setActualQty(actualQty).setMatchQty(matchQty).setOutputQty(outputQty);
			Double pression = new Double(SettingUtils.doubleDivide(matchQty, outputQty));
			if (null != pression) {
				prf.setPression(pression *100);
			}
			Double recall = new Double(SettingUtils.doubleDivide(matchQty, actualQty));
			if (null != recall) {
				prf.setRecall(100 * recall);
			}
			Double f1 = 0d;
			if (pression != null && recall != null && !(pression+recall == 0)) {
				f1 =(pression * recall *2)/(pression+recall);
				prf.setF1(f1 *100);
			}
			
			result.append(prf.getType()).append(Properties.Separator.TAB).append(String.format("%.2f",prf.getPression()))
					.append(Properties.Separator.TAB).append(String.format("%.2f",prf.getRecall())).append(Properties.Separator.TAB)
					.append(String.format("%.2f",prf.getF1())).append(Properties.Separator.LINE_FEED);
		}
		return result.toString();
		
		
	}

}
