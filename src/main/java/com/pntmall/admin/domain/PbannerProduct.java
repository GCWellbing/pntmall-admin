package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class PbannerProduct extends Domain {

	private static final long serialVersionUID = -6188526884788231109L;

	private Integer bno;
	private Integer pno;
	private String pname;
	private Integer salePrice;

	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
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



}
