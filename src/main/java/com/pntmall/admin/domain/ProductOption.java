package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductOption extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4949352643651864832L;

	private Integer pno;
	private Integer optPno;
	private String optNm;
	private Integer rank;
	
	private String pname;
	private Integer salePrice;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getOptPno() {
		return optPno;
	}
	public void setOptPno(Integer optPno) {
		this.optPno = optPno;
	}
	public String getOptNm() {
		return optNm;
	}
	public void setOptNm(String optNm) {
		this.optNm = optNm;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
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
	
}
