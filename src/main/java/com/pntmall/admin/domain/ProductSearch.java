package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class ProductSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7662995857809658089L;

	private String brand;
	private Integer pno;
	private String matnr;
	private String pname;
	private String status;
	private String deliveryType;
	private Integer cateNo;
	private Integer ptype;
	
	public Integer getPtype() {
		return ptype;
	}
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public Integer getCateNo() {
		return cateNo;
	}
	public void setCateNo(Integer cateNo) {
		this.cateNo = cateNo;
	}
	
	
}
