package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class AppPushExcel extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8031203133527689071L;

	private Integer eno;
	private Integer pno;
	private String memId;
	
	public Integer getEno() {
		return eno;
	}
	public void setEno(Integer eno) {
		this.eno = eno;
	}
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	
}
