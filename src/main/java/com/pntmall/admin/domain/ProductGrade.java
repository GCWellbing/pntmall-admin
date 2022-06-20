package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductGrade extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6058121191607748959L;

	private Integer pno;
	private Integer gradeNo;
	private String gradeName;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Integer getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}
	
}
