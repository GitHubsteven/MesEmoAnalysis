package domain;

import java.util.List;

public class DealParticipleResultBean {
	String id;
	List<String> words;
	Double positive = 0d;
	Double negative= 0d;
	Double frequency = 0d;
	
	String type;
	Double distance =0d;
	public String getId() {
		return id;
	}
	public DealParticipleResultBean setId(String id) {
		this.id = id;
		return this;
	}
	public List<String> getWords() {
		return words;
	}
	public DealParticipleResultBean setWords(List<String> words) {
		this.words = words;
		return this;
	}
	public Double getPositive() {
		return positive;
	}
	public DealParticipleResultBean setPositive(Double positive) {
		this.positive = positive;
		return this;
	}
	public Double getNegative() {
		return negative;
	}
	public DealParticipleResultBean setNegative(Double negative) {
		this.negative = negative;
		return this;
	}
	public Double getFrequency() {
		return frequency;
	}
	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "DealParticipleResultBean [id=" + id + ", words=" + words + ", positive=" + positive + ", negative="
				+ negative + ", frequency=" + frequency + ", type=" + type + ", distance=" + distance + "]";
	}
	
	
	
	
}
