package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class SeProduct extends Domain {

	private static final long serialVersionUID = 868241303919335375L;
	private Integer seno;
	private Integer pno;
	private Integer rank;
	private String pname;
	private Integer salePrice;

	public Integer getSeno() {
		return seno;
	}
	public void setSeno(Integer seno) {
		this.seno = seno;
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
	public Integer getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}



}
