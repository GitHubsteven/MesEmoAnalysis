package com.mea.domain;

public class TypeBean {
	String id;
	String type;
	
	public String getType() {
		return type;
	}
	public  TypeBean setType(String type) {
		this.type = type;
		return this;
	}
	public String getId() {
		return id;
	}
	public TypeBean setId(String id) {
		this.id = id;
		return this;
	}
	@Override
	public String toString() {
		return "TypeBean [id=" + id + ", type=" + type + "]";
	}
	
}
