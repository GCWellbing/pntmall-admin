package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class MyHealth extends SearchDomain {


	private static final long serialVersionUID = 2701157194727394953L;
	private Integer healthSeq;//헬스체크 번호
	private String year;//생년
	private Integer age;//나이
	private String gender;//성별
	private Double height;//키
	private Double weight;//몸무게
	private String gubun;
	private Integer topicCount ;
	private Double bmi;//BMI
	private String cdtion;//비만도
	private String cdtionName;//비만도
	private String healthTopic;//건강키워드
	private String status;//상태
	private String healthTopic1;//건강키워드
	private String healthTopic2;//건강키워드
	private String healthTopic3;//건강키워드
	private String healthTopicName1;
	private String healthTopicName2;
	private String healthTopicName3;
	private String femaleCondition;
	private Integer resNo;


	public Integer getHealthSeq() {
		return healthSeq;
	}

	public void setHealthSeq(Integer healthSeq) {
		this.healthSeq = healthSeq;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public Double getBmi() {
		return bmi;
	}

	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}

	public String getCdtion() {
		return cdtion;
	}

	public String getCdtionName() {
		return cdtionName;
	}

	public void setCdtionName(String cdtionName) {
		this.cdtionName = cdtionName;
	}

	public void setCdtion(String cdtion) {
		this.cdtion = cdtion;
	}

	public String getHealthTopic() {
		return healthTopic;
	}

	public void setHealthTopic(String healthTopic) {
		this.healthTopic = healthTopic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFemaleCondition() {
		return femaleCondition;
	}

	public void setFemaleCondition(String femaleCondition) {
		this.femaleCondition = femaleCondition;
	}

	public String getHealthTopicName1() {
		return healthTopicName1;
	}

	public void setHealthTopicName1(String healthTopicName1) {
		this.healthTopicName1 = healthTopicName1;
	}

	public String getHealthTopicName2() {
		return healthTopicName2;
	}

	public void setHealthTopicName2(String healthTopicName2) {
		this.healthTopicName2 = healthTopicName2;
	}

	public String getHealthTopicName3() {
		return healthTopicName3;
	}

	public void setHealthTopicName3(String healthTopicName3) {
		this.healthTopicName3 = healthTopicName3;
	}


	public String getHealthTopic1() {
		return healthTopic1;
	}

	public void setHealthTopic1(String healthTopic1) {
		this.healthTopic1 = healthTopic1;
	}

	public String getHealthTopic2() {
		return healthTopic2;
	}

	public void setHealthTopic2(String healthTopic2) {
		this.healthTopic2 = healthTopic2;
	}

	public String getHealthTopic3() {
		return healthTopic3;
	}

	public void setHealthTopic3(String healthTopic3) {
		this.healthTopic3 = healthTopic3;
	}

	public Integer getResNo() {
		return resNo;
	}

	public void setResNo(Integer resNo) {
		this.resNo = resNo;
	}

	public Integer getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}
}
