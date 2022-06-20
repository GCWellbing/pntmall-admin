package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class FaqSearch extends SearchDomain {

	private static final long serialVersionUID = -8407838264039035332L;

	private Integer faqNo;
	private String cate;
	private String question;
	private String answer;
	private String status;

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



}
