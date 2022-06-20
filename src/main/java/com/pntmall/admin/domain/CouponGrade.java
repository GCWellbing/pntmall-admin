package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class CouponGrade extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7216079683511396227L;
	
	private String couponid;
	private Integer gradeNo;
	private String gradeName;
	
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
	public Integer getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
}
