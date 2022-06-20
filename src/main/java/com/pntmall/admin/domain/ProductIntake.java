package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductIntake extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4460885836977159290L;
	
	private Integer pno;
	private Integer intakeNo;
	private Integer rank;
	private String content;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getIntakeNo() {
		return intakeNo;
	}
	public void setIntakeNo(Integer intakeNo) {
		this.intakeNo = intakeNo;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
