package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductGift extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 28847056946774209L;
	
	private Integer pno;
	private Integer giftPno;
	private String pname;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getGiftPno() {
		return giftPno;
	}
	public void setGiftPno(Integer giftPno) {
		this.giftPno = giftPno;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
}
