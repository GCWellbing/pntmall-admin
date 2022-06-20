package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Faq extends Domain {

	private static final long serialVersionUID = -4584557309692505398L;

	private Integer faqNo;
	private String cate;
	private String cateName;
	private String question;
	private String answer;
	private String status;
	private String statusName;
	private String frequentYn;

	public Integer getFaqNo() {
		return faqNo;
	}
	public void setFaqNo(Integer faqNo) {
		this.faqNo = faqNo;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getFrequentYn() {
		return frequentYn;
	}
	public void setFrequentYn(String frequentYn) {
		this.frequentYn = frequentYn;
	}


}
