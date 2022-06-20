package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Custom extends Domain {


	private static final long serialVersionUID = 3652147482388371921L;

	private Integer cno;
	private String title;
	private String rank;
	private String status;
	private String statusName;

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
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
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
