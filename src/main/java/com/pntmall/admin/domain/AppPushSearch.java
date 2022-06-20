package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class AppPushSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5802581623052281056L;

	private String title;
	private Integer status;
	private String sdate;
	private String edate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	
}
