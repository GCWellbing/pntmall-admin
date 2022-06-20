package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Team extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8651152942617459010L;

	private Integer teamNo;
	private String name;
	private String updateAuth;
	private String remark;
	private String status;
	
	private String statusName;

	public Integer getTeamNo() {
		return teamNo;
	}
	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getUpdateAuth() {
		return updateAuth;
	}
	public void setUpdateAuth(String updateAuth) {
		this.updateAuth = updateAuth;
	}
	

}
