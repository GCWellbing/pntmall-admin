package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class AdminLog extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1357813667058278251L;

	private Integer adminNo;
	private String ip;
	private String successYn;
	
	public Integer getAdminNo() {
		return adminNo;
	}
	public void setAdminNo(Integer adminNo) {
		this.adminNo = adminNo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSuccessYn() {
		return successYn;
	}
	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}
	
	
}
