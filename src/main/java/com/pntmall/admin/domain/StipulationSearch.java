package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class StipulationSearch extends SearchDomain {

	private static final long serialVersionUID = 3452668251188754045L;

	private Integer stipulationNo;
	private Integer gubun;
	private String title;
	private String content;
	private String status;

	public Integer getStipulationNo() {
		return stipulationNo;
	}
	public void setStipulationNo(Integer stipulationNo) {
		this.stipulationNo = stipulationNo;
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
