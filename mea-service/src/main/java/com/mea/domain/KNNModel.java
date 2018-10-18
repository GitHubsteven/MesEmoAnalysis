package com.mea.domain;

import java.util.List;

public class KNNModel {
	/**
	 * k值
	 */
	private Integer k;
	/**
	 * 聚类簇数
	 */
	private Integer k_medoids;
	
	/**
	 * 样本messageBean
	 */
	private List<MessageBean> modelDataList;
	/**
	 * 特征数量，每个关系类型的查准率，查全率和F1值
	 */
	
	private List<PRFBean> prfList;
	
	
	public Integer getK_medoids() {
		return k_medoids;
	}

	public void setK_medoids(Integer k_medoids) {
		this.k_medoids = k_medoids;
	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public List<MessageBean> getModelDataList() {
		return modelDataList;
	}

	public void setModelDataList(List<MessageBean> modelDataList) {
		this.modelDataList = modelDataList;
	}

	public List<PRFBean> getPrfList() {
		return prfList;
	}

	public void setPrfList(List<PRFBean> prfList) {
		this.prfList = prfList;
	}
	
	
	
}
