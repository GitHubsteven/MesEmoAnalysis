package service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import config.Properties;
import domain.KNNModel;
import domain.MessageBean;
import domain.PRFBean;
import domain.TypeBean;
import utils.DateTimeUtils;
import utils.KNN;
import utils.SettingUtils;

/**
 * created to build the model and analysis the data
 * 
 * @author dell
 *
 */
public class IAnalysisDataServiceImpl implements IAnalysisDataService {
	@Override
	public String AnalysisDataWithTestType(Path sampleContextPath, Path sampleTypePath, Path testContextPath,
			Path testTypePath, Integer k, Integer tailOringType) {

		IBuildMessageService buildDataService = new IBuildMessageServiceImpl();
		// 获取训练集
		List<MessageBean> sampleMessages = buildDataService.mergeTypeToTargetMessage(sampleContextPath, sampleTypePath);
		List<MessageBean> testMessages = buildDataService.buildTargetMessage(testContextPath);
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

		List<TypeBean> testMesTypeList = buildDataService.getTypes(testTypePath);
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

	@Override
	public String AnalysisDataWithoutTestType(Path sampleContextPath, Path sampleTypePath, Path testContextPath,
			Integer k, Integer tailOringType) {
		IBuildMessageService buildDataService = new IBuildMessageServiceImpl();
		// 获取训练集
		List<MessageBean> sampleMessages = buildDataService.mergeTypeToTargetMessage(sampleContextPath, sampleTypePath);
		List<MessageBean> testMessages = buildDataService.buildTargetMessage(testContextPath);

		StringBuilder context = new StringBuilder();
		context.append("SrcNumber").append(Properties.Separator.TAB).append("DestNumber")
				.append(Properties.Separator.TAB).append("RelationShip").append(Properties.Separator.LINE_FEED);
		testMessages.forEach(testMes -> {
			String type = KNN.knnCal(k, testMes, sampleMessages);
			testMes.setType(type);

			context.append(testMes.getSrcNumber()).append(Properties.Separator.TAB).append(testMes.getDesNumber())
					.append(Properties.Separator.TAB).append(testMes.getType()).append(Properties.Separator.LINE_FEED);
		});
		return context.toString();
	}

	@Override
	public String buildModelBySampleData(Path sampleContextPath, Path sampleTypePath, Integer k,
			Integer tailOringType) {
		KNNModel knnModel = createModelBySample(sampleContextPath, sampleTypePath, k);
		
		List<PRFBean> prfList = knnModel.getPrfList();
		StringBuilder result = new StringBuilder();
		result.append("RelationShip").append(Properties.Separator.TAB).append("Pression")
		.append(Properties.Separator.TAB).append("Recall").append(Properties.Separator.TAB).append("F_Measure")
		.append(Properties.Separator.LINE_FEED);
		prfList.forEach(prf->{
			result.append(prf.getType()).append(Properties.Separator.TAB).append(String.format("%.2f",prf.getPression()))
			.append(Properties.Separator.TAB).append(String.format("%.2f",prf.getRecall())).append(Properties.Separator.TAB)
			.append(String.format("%.2f",prf.getF1())).append(Properties.Separator.LINE_FEED);
		});
	
		return result.toString();
	}

	@Override
	public String exportModel(Path sampleContextPath, Path sampleTypePath, Integer k,
			Integer tailOringType, Path exportPath) {
		KNNModel knnModel = createModelBySample(sampleContextPath, sampleTypePath, k);
		String nowStr = DateTimeUtils.format(new Date(),DateTimeUtils.DEFAULT_FORMAT);
		exportPath = exportPath.resolve(nowStr);
		Gson gson =new Gson();
		String modelStr = gson.toJson(knnModel);
		SettingUtils.outPutTextToFile(modelStr, exportPath);
		return modelStr;
	}

	/**
	 * 根據sample来建立model，model是将sample数据分一为二来建立的，同时k值应该为输入值
	 *
	 * @param sampleContextPath 样本内容路径
	 * @param sampleTypePath 类型文件路径 
	 * @param k k值
	 * @return KNNModel KNN Model
	 */
	@Override
	public KNNModel createModelBySample(Path sampleContextPath, Path sampleTypePath, Integer k) {
		KNNModel knnModel = new KNNModel();
		
		IBuildMessageService builderMessageService = new IBuildMessageServiceImpl(); 
		List<MessageBean> sampleMessages = builderMessageService.mergeTypeToTargetMessage(sampleContextPath, sampleTypePath);
		int size = sampleMessages.size();
		int halfSize = size/2;
		
		List<MessageBean> newSampleMessages = new ArrayList<MessageBean>();
		newSampleMessages.addAll(sampleMessages.subList(0, halfSize));
		List<MessageBean> newTestMessages = sampleMessages.subList(halfSize, size);
		if (CollectionUtils.isEmpty(newTestMessages) || CollectionUtils.isEmpty(newSampleMessages)) {
			return null;
		}
		
		for(MessageBean testMes : newTestMessages){
			String type = KNN.knnCal(k, testMes, newSampleMessages);
			testMes.setKnnType(type);
		}
		
		Map<String, List<MessageBean>> testMapByType = newTestMessages.stream()
				.collect(Collectors.groupingBy(MessageBean::getType));
		Map<String, List<MessageBean>> testMapByKnnType = newTestMessages.stream()
				.collect(Collectors.groupingBy(MessageBean::getKnnType));
		
		List<PRFBean> prfList = new ArrayList<PRFBean>();
		
		
		
		//对于testMessage进行操作
		for (String type : Properties.RELATIONSHIP) {
			Integer actualQty =0;
			Integer knnAnaQty = 0;
			Integer matchQty = 0;
			
			PRFBean prf = new PRFBean();
			prf.setType(type);
			
			List<MessageBean> knnMesList = testMapByKnnType.get(type);
			List<MessageBean> actualMesList = testMapByType.get(type);
			if(CollectionUtils.isNotEmpty( actualMesList)){
				actualQty = actualMesList.size();
			}
			if(CollectionUtils.isNotEmpty(knnMesList)){
				knnAnaQty =knnMesList.size();
			}
			if(CollectionUtils.isNotEmpty(actualMesList) && CollectionUtils.isNotEmpty(knnMesList)){
				List<String> actualIds = actualMesList.stream()
						.map(MessageBean::getId)
						.collect(Collectors.toList());
				
				
				List<String> knnAnaIds = knnMesList.stream()
						.map(MessageBean::getId)
						.collect(Collectors.toList());
				matchQty = (int) actualIds.stream().filter(id->knnAnaIds.contains(id)).count();
			}
			Double precision = 0.0d;
			Double recall = 0.0d;
			if(knnAnaQty !=0){
				precision = (double) (matchQty/knnAnaQty);
			}
			if(actualQty !=0){
				recall = (double) (matchQty/actualQty);
			}
			Double fMeasure  = 0.0d;
			if(recall + precision != 0){
				fMeasure = (2* precision* recall)/(recall + precision);
			}
			prf.setActualQty(actualQty)
			.setMatchQty(matchQty)
			.setOutputQty(knnAnaQty)
			.setPression(precision *100)
			.setRecall(recall*100)
			.setF1(fMeasure);
			prfList.add(prf);
		}
		knnModel.setK(k);
		knnModel.setModelDataList(newSampleMessages);
		knnModel.setPrfList(prfList);
		return knnModel;
	}

	@Override
	public String exportModel(Path sampleContextPath, Path sampleTypePath, Integer k, Path savePath) {
		// TODO Auto-generated method stub
		return exportModel (sampleContextPath, sampleTypePath,  k,null,savePath);
	}

	@Override
	public KNNModel readKNNModelByFile(Path modelPath) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		KNNModel model = null;
		try {
			String modelJson = FileUtils.readFileToString(modelPath.toFile(), Properties.DEFUALT_CHARSET);
			if(null == modelJson){
				return null;
			}
			model = gson.fromJson(modelJson, KNNModel.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return model;
	}
	
	/**
	 * 求平均时间的权重
	 * 
	 * @param sampleMessages 样本短信
	 * @return 样本类型权重
	 */
	public Double getAverageIntWeight(List<MessageBean> sampleMessages){
		Map<String,Double> typeWeightMap = new HashMap<>();
		if(sampleMessages == null  ||sampleMessages.size() == 0 ){
			return 0D;
		}
		Double characterWeight = 1D;
		Map<String, List<MessageBean>> mapByType = sampleMessages.stream().collect(Collectors.groupingBy(MessageBean::getType));
		Properties.RELATIONSHIP.forEach(type->{
			List<MessageBean> typeMessages = mapByType.get(type);
			Double typeWeight = 1D;
			if(typeMessages != null && typeMessages.size() !=0){
				OptionalDouble averageOpt = typeMessages.stream().mapToDouble(MessageBean::getAverageInteval).average();
				typeWeight = averageOpt.orElse(1D);
			}
			typeWeightMap.put(type, typeWeight);
		});
		
		characterWeight = getDX((List)typeWeightMap.values());
		
		return characterWeight;
	}
	/**
	 * 求方差
	 * 
	 * @param data 值
	 * @return
	 */
	Double getDX(List<Double> data){
		//平均数
		Double e = data.stream().mapToDouble(a->a).average().orElse(0);
		double D = data.stream().mapToDouble(a->(a-e)*(a-e)).average().orElse(1);
		return D;
	}
	
	
}
