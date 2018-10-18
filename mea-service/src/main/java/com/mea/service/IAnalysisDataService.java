package com.mea.service;

import com.mea.domain.KNNModel;

import java.nio.file.Path;

;

/**
 * 建立KNN 模型
 *
 * @author dell
 */
public interface IAnalysisDataService {
    /**
     * 建立模型和分析测试数据
     *
     * @param sampleContextPath 样本内容路径
     * @param sampleTypePath    样本类型路径
     * @param testContextPath   测试内容路径
     * @param testTypePath      测试内容路径
     * @param k                 k值
     * @param tailOringType     剪裁类型
     * @return List<MessageBean>
     */
    String AnalysisDataWithTestType(Path sampleContextPath, Path sampleTypePath,
                                    Path testContextPath, Path testTypePath, Integer k, Integer tailOringType);

    /**
     * 建立模型和分析测试数据
     *
     * @param sampleContextPath 样本内容路径
     * @param sampleTypePath    样本类型路径
     * @param testContextPath   测试内容路径
     * @param k                 k值
     * @param tailOringType     剪裁类型
     * @return List<MessageBean>
     */
    String AnalysisDataWithoutTestType(Path sampleContextPath, Path sampleTypePath,
                                       Path testContextPath, Integer k, Integer tailOringType);

    /**
     * 通过训练集建立KNN模型，并且保存到指定的文件目录下，以供后面使用
     *
     * @param sampleContextPath 样本内容路径
     * @param sampleTypePath    样本类型路径
     * @param k                 k值
     * @param tailOringType     剪裁类型
     * @return List<MessageBean>
     */
    String buildModelBySampleData(Path sampleContextPath, Path sampleTypePath, Integer k, Integer tailOringType);

    /**
     * 通过训练集建立KNN模型，并且保存到指定的文件目录下，以供后面使用
     *
     * @param sampleContextPath 样本内容路径
     * @param sampleTypePath    样本类型路径
     * @param k                 k值
     * @param tailOringType     剪裁类型
     * @return List<MessageBean>
     */
    String exportModel(Path sampleContextPath, Path sampleTypePath, Integer k, Integer tailOringType, Path savePath);


    /**
     * overload exportModel
     *
     * @param sampleContextPath 样本内容路径
     * @param sampleTypePath    样本类型路径
     * @param k                 k值
     * @return String
     */
    String exportModel(Path sampleContextPath, Path sampleTypePath, Integer k, Path savePath);


    /**
     * 根据路径获得KNNModel
     *
     * @return KNNModeldel
     */
    KNNModel readKNNModelByFile(Path modelPath);

    /**
     * 根據sample来建立model，model是将sample数据分一为二来建立的，同时k值应该为输入值
     *
     * @param sampleContextPath 样本内容路径
     * @param sampleTypePath    类型文件路径
     * @param k                 k值
     * @return KNNModel KNN Model
     */
    KNNModel createModelBySample(Path sampleContextPath, Path sampleTypePath, Integer k);

}
