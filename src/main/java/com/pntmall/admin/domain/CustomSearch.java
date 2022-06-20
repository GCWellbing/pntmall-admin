package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class CustomSearch extends SearchDomain {

	private static final long serialVersionUID = -6226324508313758203L;

	private Integer cno;
	private String title;
	private String status;

	public Integer getCno() {
		return cno;
	}
	public void setCno(Integer cno) {
		this.cno = cno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
