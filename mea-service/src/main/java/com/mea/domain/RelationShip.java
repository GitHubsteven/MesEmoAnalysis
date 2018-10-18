package com.mea.domain;

public enum RelationShip {
	
	
	Relatives("1"),
	Friends("2"),
	Colleague("3"),
	Stranger("4");
	
	private String value;
	
	RelationShip(String value){
		this.value = value;
	}
	String getValue (){
		return this.value;
	}
}
