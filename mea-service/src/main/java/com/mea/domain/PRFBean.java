package com.mea.domain;

public class PRFBean {
	String type;
	//实际上数量
	Integer actualQty;
	//程序运行出来的数量
	Integer outputQty;
	//匹配的数量
	Integer matchQty;
	
	Double pression;
	Double recall;
	Double f1;
	public String getType() {
		return type;
	}
	public PRFBean setType(String type) {
		this.type = type;
		return this;
	}
	public Double getPression() {
		return pression;
	}
	public PRFBean setPression(Double pression) {
		this.pression = pression;
		return this;
	}
	public Double getRecall() {
		return recall;
	}
	public PRFBean setRecall(Double recall) {
		this.recall = recall;
		return this;
	}
	public Double getF1() {
		return f1;
	}
	public PRFBean setF1(Double f1) {
		this.f1 = f1;
		return this;
	}
	
	
	public Integer getActualQty() {
		return actualQty;
	}
	public PRFBean setActualQty(Integer actualQty) {
		this.actualQty = actualQty;
		return this;
	}
	public Integer getOutputQty() {
		return outputQty;
	}
	public PRFBean setOutputQty(Integer outputQty) {
		this.outputQty = outputQty;
		return this;
	}
	public Integer getMatchQty() {
		return matchQty;
	}
	public PRFBean setMatchQty(Integer matchQty) {
		this.matchQty = matchQty;
		return this;
	}
	@Override
	public String toString() {
		return "PRFBean [type=" + type + ", actualQty=" + actualQty + ", outputQty=" + outputQty + ", matchQty="
				+ matchQty + ", pression=" + pression + ", recall=" + recall + ", f1=" + f1 + "]";
	}
	public PRFBean() {
		this.actualQty = 0;
		//程序运行出来的数量
		this.outputQty = 0;
		//匹配的数量
		this.matchQty = 0;
		
		this.pression = 0d;
		this.recall = 0d;
		this.f1 =0d;
	}
}
