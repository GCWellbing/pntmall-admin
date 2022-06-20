package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class OrderStatusCode extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4578026897229811075L;

	private String status;
	private String boName;
	private String feName;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBoName() {
		return boName;
	}
	public void setBoName(String boName) {
		this.boName = boName;
	}
	public String getFeName() {
		return feName;
	}
	public void setFeName(String feName) {
		this.feName = feName;
	}
	
}
