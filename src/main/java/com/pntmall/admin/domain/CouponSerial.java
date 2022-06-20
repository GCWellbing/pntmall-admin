package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class CouponSerial extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5474778899731104830L;

	private Integer seq;
	private String seq64;
	private String couponid;
	private String serial;
	private Integer memNo;
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSeq64() {
		return seq64;
	}
	public void setSeq64(String seq64) {
		this.seq64 = seq64;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	
		
}
