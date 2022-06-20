package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class CustomProduct extends Domain {

	private static final long serialVersionUID = 7619188121702856839L;

	private Integer cno;
	private Integer ages;
	private Integer pno;
	private String matnr;
	private String pname;
	private Integer salePrice;
	private Integer rank;

	public Integer getCno() {
		return cno;
	}
	public void setCno(Integer cno) {
		this.cno = cno;
	}
	public Integer getAges() {
		return ages;
	}
	public void setAges(Integer ages) {
		this.ages = ages;
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
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}




}
