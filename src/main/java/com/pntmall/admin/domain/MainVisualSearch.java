package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class MainVisualSearch extends SearchDomain {

	private static final long serialVersionUID = -7410126820775657833L;

	private Integer mvNo;
	private String title;
	private String status;
	private String fromDate;
	private String toDate;

	public Integer getMvNo() {
		return mvNo;
	}
	public void setMvNo(Integer mvNo) {
		this.mvNo = mvNo;
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}



}
