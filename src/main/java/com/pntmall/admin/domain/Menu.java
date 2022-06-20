package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Menu extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5127161205400024125L;

	private Integer menuNo;
	private Integer pmenuNo;
	private String name;
	private String url;
	private Integer rank;
	private String status;
	
	private Integer level;
	private String authYn;
	
	public Integer getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(Integer menuNo) {
		this.menuNo = menuNo;
	}
	public Integer getPmenuNo() {
		return pmenuNo;
	}
	public void setPmenuNo(Integer pmenuNo) {
		this.pmenuNo = pmenuNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuthYn() {
		return authYn;
	}
	public void setAuthYn(String authYn) {
		this.authYn = authYn;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
