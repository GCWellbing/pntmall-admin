package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class PexhibitionSearch extends SearchDomain {

	private static final long serialVersionUID = 224534507911062115L;

	private Integer bno;
	private String title;
	private String status;
	private String fromDate;
	private String toDate;

	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
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
