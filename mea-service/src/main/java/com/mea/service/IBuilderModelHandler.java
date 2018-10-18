package com.mea.service;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import com.mea.domain.KNNModel;
import com.mea.domain.MessageBean;
import com.mea.domain.TypeBean;

public interface IBuilderModelHandler {

    /**
     * 根据路径获取messages
     *
     * @param sampleContextPath
     * @return
     */
    List<MessageBean> getSampleMessages(Path sampleContextPath);

    /**
     * 根据路径获取sample typeValue
     *
     * @param sampleTypePath
     * @return
     */
    List<TypeBean> getSampleTypes(Path sampleTypePath);


    /**
     * 获取测试messages
     *
     * @param testContextPath
     * @param testTypePath
     * @return
     */
    default List<MessageBean> getTestMessages(Path testContextPath, Path testTypePath) {
        return Collections.EMPTY_LIST;
    }

    ;

    /**
     * 获取测试messages
     *
     * @return
     */
    default List<MessageBean> getTestMessages() {
        return Collections.EMPTY_LIST;
    }

    ;


    /**
     * 獲取k 值
     *
     * @return
     */
    default Integer getK() {
        return null;
    }

    ;

    /**
     * 獲取k值
     *
     * @param k
     * @return
     */
    default Integer getK(Integer k) {
        return k;
    }

    /**
     * 獲取有测试类型的分析结果
     *
     * @param sampleMessages
     * @param testMessages
     * @param K
     * @return
     */
    default String getResult(List<MessageBean> sampleMessages, List<MessageBean> testMessages, Integer K) {
        return null;
    }

    ;

    /**
     * 获取无参数比较结果
     *
     * @param sampleMessages
     * @param testMessages
     * @param K
     * @return
     */
    default String getResultNoTestType(List<MessageBean> sampleMessages, List<MessageBean> testMessages, Integer K) {
        return null;
    }

    ;

    /**
     * 导出模型
     *
     * @param model
     */
    default void exportModel(KNNModel model) {

    }


}
