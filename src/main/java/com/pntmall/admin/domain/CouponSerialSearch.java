package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class CouponSerialSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4639520192801421940L;
	
	private String couponid;
	
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
		
}
