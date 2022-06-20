package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Cart extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5922140163962044519L;
	
	private String cartid;
	private Integer gubun;
	private Integer memNo;
	private Integer pno;
	private Integer qty;
	private String directYn;
	private String orderYn;
	
	public String getCartid() {
		return cartid;
	}
	public void setCartid(String cartid) {
		this.cartid = cartid;
	}
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
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
	public String getDirectYn() {
		return directYn;
	}
	public void setDirectYn(String directYn) {
		this.directYn = directYn;
	}
	public String getOrderYn() {
		return orderYn;
	}
	public void setOrderYn(String orderYn) {
		this.orderYn = orderYn;
	}

	
}
