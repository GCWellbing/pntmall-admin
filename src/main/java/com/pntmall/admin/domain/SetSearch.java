package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class SetSearch extends SearchDomain {

	private static final long serialVersionUID = -8522072324525956308L;

	private Integer sno;
	private String title;
	private String status;
	private String fromDate;
	private String toDate;
	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
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
