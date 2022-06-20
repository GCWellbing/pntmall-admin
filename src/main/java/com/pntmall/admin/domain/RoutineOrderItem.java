package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class RoutineOrderItem extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3667627488253740503L;
	
	private String orderid;
	private Integer pno;
	private Integer qty;
	private String pname;
	private String matnr;
	private Integer salePrice;
	private Integer memPrice;
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
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
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public Integer getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getMemPrice() {
		return memPrice;
	}
	public void setMemPrice(Integer memPrice) {
		this.memPrice = memPrice;
	}
	
}
