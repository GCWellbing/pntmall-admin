package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductDiscount extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5963240793606726781L;
	
	private Integer pno;
	private Integer gradeNo;
	private Integer discount;
	
	private String gradeName;

	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
}
