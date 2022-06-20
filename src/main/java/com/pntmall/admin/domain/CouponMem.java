package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class CouponMem extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2946122247454229911L;

	private String couponid;
	private Integer memNo;
	private String memId;
	
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	
}
