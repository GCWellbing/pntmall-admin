package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class AdminSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2463761316645805201L;

	private String adminId;
	private String name;
	private String status;
	private Integer teamNo;
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTeamNo() {
		return teamNo;
	}
	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}
	
	
}
