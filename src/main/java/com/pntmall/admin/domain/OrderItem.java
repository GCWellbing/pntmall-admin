package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class OrderItem extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6650382441733252993L;

	private Integer itemNo;
	private String orderid;
	private Integer shipNo;
	private Integer pno;
	private Integer qty;
	private String pname;
	private Integer salePrice;
	private Integer memPrice;
	private Integer discount;
	private Integer applyPrice;
	private Integer point;
	private Integer returnQty;
	private Integer exchangeQty;
	private String mcouponid;
	private Integer orgItemNo;
	private String matnr;
	private String orgOrderid;
	private String statusName;
	private String coldYn;
	private String gift;
	private Integer doseMonth;
	private Integer dosage;
	private Integer doseCnt;
	private String doseMethod;
	private Integer netpr;
	private Integer supplyPrice;
	
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
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getApplyPrice() {
		return applyPrice;
	}
	public void setApplyPrice(Integer applyPrice) {
		this.applyPrice = applyPrice;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getReturnQty() {
		return returnQty;
	}
	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}
	public String getMcouponid() {
		return mcouponid;
	}
	public void setMcouponid(String mcouponid) {
		this.mcouponid = mcouponid;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getOrgOrderid() {
		return orgOrderid;
	}
	public void setOrgOrderid(String orgOrderid) {
		this.orgOrderid = orgOrderid;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public Integer getShipNo() {
		return shipNo;
	}
	public void setShipNo(Integer shipNo) {
		this.shipNo = shipNo;
	}
	public Integer getOrgItemNo() {
		return orgItemNo;
	}
	public void setOrgItemNo(Integer orgItemNo) {
		this.orgItemNo = orgItemNo;
	}
	public String getColdYn() {
		return coldYn;
	}
	public void setColdYn(String coldYn) {
		this.coldYn = coldYn;
	}
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
	}
	public Integer getExchangeQty() {
		return exchangeQty;
	}
	public void setExchangeQty(Integer exchangeQty) {
		this.exchangeQty = exchangeQty;
	}
	public Integer getDoseMonth() {
		return doseMonth;
	}
	public void setDoseMonth(Integer doseMonth) {
		this.doseMonth = doseMonth;
	}
	public Integer getDosage() {
		return dosage;
	}
	public void setDosage(Integer dosage) {
		this.dosage = dosage;
	}
	public Integer getDoseCnt() {
		return doseCnt;
	}
	public void setDoseCnt(Integer doseCnt) {
		this.doseCnt = doseCnt;
	}
	public String getDoseMethod() {
		return doseMethod;
	}
	public void setDoseMethod(String doseMethod) {
		this.doseMethod = doseMethod;
	}
	public Integer getNetpr() {
		return netpr;
	}
	public void setNetpr(Integer netpr) {
		this.netpr = netpr;
	}
	public Integer getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(Integer supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	
}
