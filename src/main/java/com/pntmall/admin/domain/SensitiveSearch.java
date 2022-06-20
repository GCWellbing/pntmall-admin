package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class SensitiveSearch extends SearchDomain {

	private static final long serialVersionUID = -4991206681999868758L;
	private Integer sensitiveNo;
	private String title;
	private String content;
	private String status;
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



}
