package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class SetProduct extends Domain {

	private static final long serialVersionUID = -8575292639601180177L;

	private Integer sno;
	private Integer pno;
	private String matnr;
	private String pname;
	private Integer salePrice;
	private Integer rank;

	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
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
