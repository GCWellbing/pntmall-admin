package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class PrivacySearch extends SearchDomain {

	private static final long serialVersionUID = 8027631723165485510L;

	private Integer privacyNo;
	private Integer gubun;
	private String title;
	private String content;
	private String status;

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
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}


}
