package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Sensitive extends Domain {

	private static final long serialVersionUID = -324795239841618270L;
	private Integer sensitiveNo;
	private String title;
	private String content;
	private String status;
	private String statusName;
	public Integer getSensitiveNo() {
		return sensitiveNo;
	}
	public void setSensitiveNo(Integer sensitiveNo) {
		this.sensitiveNo = sensitiveNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
