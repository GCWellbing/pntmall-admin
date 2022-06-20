package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductCategory extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2104400251467837846L;

	private Integer pno;
	private Integer cateNo;
	private String cateName;
	private String pcateName;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getCateNo() {
		return cateNo;
	}
	public void setCateNo(Integer cateNo) {
		this.cateNo = cateNo;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getPcateName() {
		return pcateName;
	}
	public void setPcateName(String pcateName) {
		this.pcateName = pcateName;
	}
	
}
