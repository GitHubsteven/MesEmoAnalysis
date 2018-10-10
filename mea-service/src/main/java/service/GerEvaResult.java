package service;

import config.Properties;
import domain.AllPRFValueBean;
import domain.KNNModel;
import domain.MessageBean;
import domain.PRFBean;
import org.apache.commons.collections4.CollectionUtils;
import utils.KNN;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GerEvaResult {

    /**
     * 按照某个只来排序，以方便获取对应的K值，这是以家人pression排序的。
     *
     * @param allPrfList
     * @param sortName
     * @return
     */
    public List<AllPRFValueBean> sortPrfListByReletivesPre(List<AllPRFValueBean> allPrfList, String sortName) {
        allPrfList.sort((prf1, prf2) -> prf1.getReletivesPression().compareTo(prf2.getReletivesPression()));
        return allPrfList;
    }

    /**
     * 根据k值范围来求评价的结果
     *
     * @param sampleContextPath
     * @param sampleTypePath
     * @param k
     * @return
     */
    public List<AllPRFValueBean> getEvaRsultByKs(Path sampleContextPath, Path sampleTypePath, Integer k) {
        List<AllPRFValueBean> allPRFList = new ArrayList<>();
        for (int j = 1; j <= k; j++) {
            KNNModel KNNBean = createModelBySample(sampleContextPath, sampleTypePath, k);
            List<PRFBean> prfList = KNNBean.getPrfList();
            AllPRFValueBean allPrf = new AllPRFValueBean();
            allPrf.setReletivesPression(prfList.get(0).getPression()).setReletivesRecall(prfList.get(0).getRecall()).setReletivesF1(prfList.get(0).getF1())
                    .setFriendPression(prfList.get(1).getPression()).setFriendRecall(prfList.get(1).getRecall()).setFriendF1(prfList.get(1).getF1())
                    .setClassmatePression(prfList.get(2).getPression()).setClassmateRecall(prfList.get(2).getRecall()).setClassmateF1(prfList.get(2).getF1())
                    .setColleaguePression(prfList.get(3).getPression()).setColleagueRecall(prfList.get(3).getRecall()).setFriendF1(prfList.get(3).getF1())
                    .setStrangerPression(prfList.get(4).getPression()).setStrangerRecall(prfList.get(4).getRecall()).setColleagueF1(prfList.get(4).getF1())
                    .setSystemPression(prfList.get(5).getPression()).setSystemRecall(prfList.get(5).getRecall()).setSystemF1(prfList.get(5).getF1())
                    .setK(j);
            allPRFList.add(allPrf);
        }
        return allPRFList;
    }

    ;

    /**
     * 根據sample来建立model，model是将sample数据分一为二来建立的，同时k值应该为输入值
     *
     * @param sampleContextPath 样本内容路径
     * @param sampleTypePath    类型文件路径
     * @param k                 k值
     * @return KNNModel KNN Model
     */
    public KNNModel createModelBySample(Path sampleContextPath, Path sampleTypePath, Integer k) {
        KNNModel knnModel = new KNNModel();

        IBuildMessageService builderMessageService = new IBuildMessageServiceImpl();
        List<MessageBean> sampleMessages = builderMessageService.mergeTypeToTargetMessage(sampleContextPath, sampleTypePath);
        int size = sampleMessages.size();
        int halfSize = size / 2;

        List<MessageBean> newSampleMessages = new ArrayList<MessageBean>(sampleMessages.subList(0, halfSize));
        List<MessageBean> newTestMessages = sampleMessages.subList(halfSize, size);
        if (CollectionUtils.isEmpty(newTestMessages) || CollectionUtils.isEmpty(newSampleMessages)) {
            return null;
        }

        for (MessageBean testMes : newTestMessages) {
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
            Integer actualQty = 0;
            Integer knnAnaQty = 0;
            Integer matchQty = 0;

            PRFBean prf = new PRFBean();
            prf.setType(type);

            List<MessageBean> knnMesList = testMapByKnnType.get(type);
            List<MessageBean> actualMesList = testMapByType.get(type);
            if (CollectionUtils.isNotEmpty(actualMesList)) {
                actualQty = actualMesList.size();
            }
            if (CollectionUtils.isNotEmpty(knnMesList)) {
                knnAnaQty = knnMesList.size();
            }
            if (CollectionUtils.isNotEmpty(actualMesList) && CollectionUtils.isNotEmpty(knnMesList)) {
                List<String> actualIds = actualMesList.stream()
                        .map(MessageBean::getId)
                        .collect(Collectors.toList());


                List<String> knnAnaIds = knnMesList.stream()
                        .map(MessageBean::getId)
                        .collect(Collectors.toList());
                matchQty = (int) actualIds.stream().filter(id -> knnAnaIds.contains(id)).count();
            }
            Double precision = 0.0d;
            Double recall = 0.0d;
            if (knnAnaQty != 0) {
                precision = (double) (matchQty / knnAnaQty);
            }
            if (actualQty != 0) {
                recall = (double) (matchQty / actualQty);
            }
            Double fMeasure = 0.0d;
            if (recall + precision != 0) {
                fMeasure = (2 * precision * recall) / (recall + precision);
            }
            prf.setActualQty(actualQty)
                    .setMatchQty(matchQty)
                    .setOutputQty(knnAnaQty)
                    .setPression(precision * 100)
                    .setRecall(recall * 100)
                    .setF1(fMeasure);
            prfList.add(prf);
        }
        knnModel.setK(k);
        knnModel.setModelDataList(newSampleMessages);
        knnModel.setPrfList(prfList);
        return knnModel;
    }

}	
