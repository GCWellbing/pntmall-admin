package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class MagazineProduct extends Domain {

	private static final long serialVersionUID = 3136603929791491955L;

	private Integer mno;
	private Integer pno;
	private String matnr;
	private String pname;
	private Integer salePrice;
	private Integer rank;

	public Integer getMno() {
		return mno;
	}
	public void setMno(Integer mno) {
		this.mno = mno;
	}
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}




}
