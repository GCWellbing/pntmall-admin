package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class OrderGift extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2215680556766215805L;

	private Integer giftNo;
	private Integer itemNo;
	private Integer pno;
	private Integer qty;
	private String pname;
	
	public Integer getGiftNo() {
		return giftNo;
	}
	public void setGiftNo(Integer giftNo) {
		this.giftNo = giftNo;
	}
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
}
