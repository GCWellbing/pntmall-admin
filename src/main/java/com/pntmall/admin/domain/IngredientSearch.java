package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class IngredientSearch extends SearchDomain {

	private static final long serialVersionUID = 3265020289606449531L;

	private Integer ino;
	private String title;
	private String status;

	public Integer getIno() {
		return ino;
	}
	public void setIno(Integer ino) {
		this.ino = ino;
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
