package com.mea.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mea.domain.MessageBean;

public class KNN {
	
    public KNN(List<MessageBean> KNNDS) {
    }

    //欧式距离
    private static double disCal(MessageBean i, MessageBean td) {
        return Math.sqrt(
        		(i.getAverageInteval() - td.getAverageInteval())*(i.getAverageInteval() - td.getAverageInteval())+
        		(i.getVariance() - td.getVariance())*(i.getVariance() - td.getVariance())+
                (i.getEmotion() - td.getEmotion())*(i.getEmotion() - td.getEmotion())+
                (i.getObviousValue1() - td.getObviousValue1())*(i.getObviousValue1() - td.getObviousValue1())+
        		(i.getObviousValue2() - td.getObviousValue2())*(i.getObviousValue2() - td.getObviousValue2())+
                (i.getObviousValue3() - td.getObviousValue3())*(i.getObviousValue3() - td.getObviousValue3())+
                (i.getObviousValue4() - td.getObviousValue4())*(i.getObviousValue4() - td.getObviousValue4())+
        		(i.getObviousValue5() - td.getObviousValue5())*(i.getObviousValue5() - td.getObviousValue5())+
                (i.getObviousValue6() - td.getObviousValue6())*(i.getObviousValue6() - td.getObviousValue6())
                );
    }

    private static String getMaxValueKey(int k, List<MessageBean> ts){
        //只保留前k个元素

        while(ts.size() != k) {
            ts.remove(k);
        }

        String sKey;
        //保存key以及出现次数
        HashMap<String,Integer> keySet = new HashMap<String,Integer>();
        keySet.put(ts.get(0).getType(),1);
        for (int x = 1; x < ts.size(); x++) {
            sKey = ts.get(x).getType();
            if (keySet.containsKey(sKey)) {
                keySet.put(sKey, keySet.get(sKey)+1);
            } else {
                keySet.put(sKey, 1);
            }
        }
        Set<Map.Entry<String,Integer>> set = keySet.entrySet();
        Iterator<Map.Entry<String,Integer>> iter = set.iterator();

        int mValue = 0;
        String mType = "";
        while (iter.hasNext()){
            Map.Entry<String,Integer> map = iter.next();
            if (mValue < map.getValue()) {
                mType = map.getKey();
                mValue = map.getValue();
            }
        }

        return mType;
    }

    public static String knnCal(int k, MessageBean i, List<MessageBean> ts) {
        //保存距离
        for (MessageBean td : ts) {
            td.setDistance(disCal(i, td));
        }
        Collections.sort(ts);
        return getMaxValueKey(k, ts);
    }
}
