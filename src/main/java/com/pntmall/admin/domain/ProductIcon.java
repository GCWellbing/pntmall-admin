package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductIcon extends Domain {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1632540428946378394L;
	
	private Integer pno;
	private Integer iconNo;
	private String content;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getIconNo() {
		return iconNo;
	}
	public void setIconNo(Integer iconNo) {
		this.iconNo = iconNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
