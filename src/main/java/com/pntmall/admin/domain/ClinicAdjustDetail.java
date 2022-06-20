package com.pntmall.admin.domain;

import java.util.Date;

import com.pntmall.common.type.Domain;

public class ClinicAdjustDetail extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2103893930654492024L;

	private String orderid;
	private Integer gubun;
	private Integer orderGubun;
	private Integer point;
	private Integer shipAmt;
	private Integer totDiscount;
	private Integer payAmt;
	private String status;
	private String boName;
	private String pname;
	private Integer itemCnt;
	private Integer itemQty;
	private Integer supplyAmt;
	private Integer saleAmt;
	private Date confirmDate;
	private Date returnDate;
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}
	public Integer getOrderGubun() {
		return orderGubun;
	}
	public void setOrderGubun(Integer orderGubun) {
		this.orderGubun = orderGubun;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getShipAmt() {
		return shipAmt;
	}
	public void setShipAmt(Integer shipAmt) {
		this.shipAmt = shipAmt;
	}
	public Integer getTotDiscount() {
		return totDiscount;
	}
	public void setTotDiscount(Integer totDiscount) {
		this.totDiscount = totDiscount;
	}
	public Integer getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(Integer payAmt) {
		this.payAmt = payAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBoName() {
		return boName;
	}
	public void setBoName(String boName) {
		this.boName = boName;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Integer getItemCnt() {
		return itemCnt;
	}
	public void setItemCnt(Integer itemCnt) {
		this.itemCnt = itemCnt;
	}
	public Integer getItemQty() {
		return itemQty;
	}
	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}
	public Integer getSupplyAmt() {
		return supplyAmt;
	}
	public void setSupplyAmt(Integer supplyAmt) {
		this.supplyAmt = supplyAmt;
	}
	public Integer getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(Integer saleAmt) {
		this.saleAmt = saleAmt;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
}
