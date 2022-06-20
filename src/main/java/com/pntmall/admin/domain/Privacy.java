package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Privacy extends Domain {

	/**
	 *
	 */
	private static final long serialVersionUID = -5077830354861995911L;

	private Integer privacyNo;
	private Integer gubun;
	private String title;
	private String content;
	private String status;
	private String statusName;

	public Integer getPrivacyNo() {
		return privacyNo;
	}
	public void setPrivacyNo(Integer privacyNo) {
		this.privacyNo = privacyNo;
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
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}



}
