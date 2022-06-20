package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Admin extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 187828191057993367L;
	
	private Integer adminNo;
	private String adminId;
	private String passwd;
	private String passwd2;
	private String passwd3;
	private String name;
	private String email;
	private String mtel;
	private String updateAuth;
	private Integer teamNo;
	private Integer loginFailCnt;
	private String status;
	
	private String statusName;
	private String teamName;
	
	public Integer getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(Integer adminNo) {
		this.adminNo = adminNo;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMtel() {
		return mtel;
	}

	public void setMtel(String mtel) {
		this.mtel = mtel;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public Integer getLoginFailCnt() {
		return loginFailCnt;
	}

	public void setLoginFailCnt(Integer loginFailCnt) {
		this.loginFailCnt = loginFailCnt;
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUpdateAuth() {
		return updateAuth;
	}

	public void setUpdateAuth(String updateAuth) {
		this.updateAuth = updateAuth;
	}

	public String getPasswd2() {
		return passwd2;
	}

	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}

	public String getPasswd3() {
		return passwd3;
	}

	public void setPasswd3(String passwd3) {
		this.passwd3 = passwd3;
	}
	

}
