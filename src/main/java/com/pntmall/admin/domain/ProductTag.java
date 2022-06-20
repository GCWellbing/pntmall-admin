package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductTag extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1129976652898244596L;

	private Integer pno;
	private String tag;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
