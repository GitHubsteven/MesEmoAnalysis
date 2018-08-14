package domain;

/**
 * 用来保存Message中所需要的信息
 *
 * Created by rongbin.xie on 2017/7/13.
 */
public class MessageBean implements Comparable<MessageBean> {
	String id;
    String text;
    String srcNumber;
    String desNumber;
    Double frequency;
    Double positive = 0d;
	Double negative= 0d;
	
	String type;
	Double distance =0d;
	String knnType;
	
	Double averageInteval;
	Double variance;
	Double emotion;
	Double obviousValue1;
	Double obviousValue2;
	Double obviousValue3;
	Double obviousValue4;
	Double obviousValue5;
	Double obviousValue6;
	
    
    public String getText() {
        return text;
    }

    public MessageBean setText(String text) {
        this.text = text;
        return this;
    }

    public Double getFrequency() {
        return frequency;
    }

    public MessageBean setFrequency(Double frequency) {
        this.frequency = frequency;
        return this;
    }
    
    

    public String getId() {
		return id;
	}

	public MessageBean setId(String id) {
		this.id = id;
		return this;
	}

	public MessageBean(String text, Double frequency) {
        this.text = text;
        this.frequency = frequency;
    }
    
    public MessageBean(){}

	public Double getPositive() {
		return positive;
	}

	public MessageBean setPositive(Double positive) {
		this.positive = positive;
		return this;
	}

	public Double getNegative() {
		return negative;
	}

	public MessageBean setNegative(Double negative) {
		this.negative = negative;
		return this;
	}

	public String getType() {
		return type;
	}

	public MessageBean setType(String type) {
		this.type = type;
		return this;
	}

	public Double getDistance() {
		return distance;
	}

	public MessageBean setDistance(Double distance) {
		this.distance = distance;
		return this;
	}
	

	public String getSrcNumber() {
		return srcNumber;
	}

	public MessageBean setSrcNumber(String srcNumber) {
		this.srcNumber = srcNumber;
		return this;
	}

	public String getDesNumber() {
		return desNumber;
	}

	public MessageBean setDesNumber(String desNumber) {
		this.desNumber = desNumber;
		return this;
	}

	@Override
	public String toString() {
		return "MessageBean [id=" + id + ", text=" + text + ", srcNumber=" + srcNumber + ", desNumber=" + desNumber
				+ ", frequency=" + frequency + ", positive=" + positive + ", negative=" + negative + ", type=" + type
				+ ", distance=" + distance + "]";
	}

	public MessageBean(String id, String text, Double frequency, Double positive, Double negative, String type,
			Double distance) {
		super();
		this.id = id;
		this.text = text;
		this.frequency = frequency;
		this.positive = positive;
		this.negative = negative;
		this.type = type;
		this.distance = distance;
	}

	public MessageBean(Double frequency, Double positive, Double negative, String type
			) {
		super();
		this.frequency = frequency;
		this.positive = positive;
		this.negative = negative;
		this.type = type;
	}
	
	
	public String getKnnType() {
		return knnType;
	}

	public void setKnnType(String knnType) {
		this.knnType = knnType;
	}

	@Override
    public int compareTo(MessageBean arg0) {
        return this.distance.compareTo(Double.valueOf(arg0.distance));
    }

	public Double getAverageInteval() {
		return averageInteval;
	}

	public void setAverageInteval(Double averageInteval) {
		this.averageInteval = averageInteval;
	}

	public Double getVariance() {
		return variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}

	public Double getEmotion() {
		return emotion;
	}

	public void setEmotion(Double emotion) {
		this.emotion = emotion;
	}

	public Double getObviousValue1() {
		return obviousValue1;
	}

	public void setObviousValue1(Double obviousValue1) {
		this.obviousValue1 = obviousValue1;
	}

	public Double getObviousValue2() {
		return obviousValue2;
	}

	public void setObviousValue2(Double obviousValue2) {
		this.obviousValue2 = obviousValue2;
	}

	public Double getObviousValue3() {
		return obviousValue3;
	}

	public void setObviousValue3(Double obviousValue3) {
		this.obviousValue3 = obviousValue3;
	}

	public Double getObviousValue4() {
		return obviousValue4;
	}

	public void setObviousValue4(Double obviousValue4) {
		this.obviousValue4 = obviousValue4;
	}

	public Double getObviousValue5() {
		return obviousValue5;
	}

	public void setObviousValue5(Double obviousValue5) {
		this.obviousValue5 = obviousValue5;
	}

	public Double getObviousValue6() {
		return obviousValue6;
	}

	public void setObviousValue6(Double obviousValue6) {
		this.obviousValue6 = obviousValue6;
	} 
	
	
}
