package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Intake extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6871581765826937782L;

	private Integer intakeNo;
	private String content;
	private String status;
	private String statusName;
	
	public Integer getIntakeNo() {
		return intakeNo;
	}
	public void setIntakeNo(Integer intakeNo) {
		this.intakeNo = intakeNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
}
