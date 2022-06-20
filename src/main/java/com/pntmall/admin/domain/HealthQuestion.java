package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class HealthQuestion extends Domain {
	private static final long serialVersionUID = 6294802525437269685L;

	private Integer healthNo;	//건강주제 순번
	private String question;	//질문
	private String type;	//유형
	private String naYn;	//해당없음 여부
	private String etcYn;	//기타 여부
	private String popupYn;	//안내팝업 여부

	public Integer getHealthNo() {
		return healthNo;
	}

	public void setHealthNo(Integer healthNo) {
		this.healthNo = healthNo;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNaYn() {
		return naYn;
	}

	public void setNaYn(String naYn) {
		this.naYn = naYn;
	}

	public String getEtcYn() {
		return etcYn;
	}

	public void setEtcYn(String etcYn) {
		this.etcYn = etcYn;
	}

	public String getPopupYn() {
		return popupYn;
	}

	public void setPopupYn(String popupYn) {
		this.popupYn = popupYn;
	}
}
