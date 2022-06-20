package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class CouponProduct extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7606584169219893613L;

	private String couponid;
	private Integer pno;
	
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	
}
