package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class TeamMenuAuth extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -374687230556472430L;

	private Integer teamNo;
	private Integer menuNo;
	
	public Integer getTeamNo() {
		return teamNo;
	}
	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}
	public Integer getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(Integer menuNo) {
		this.menuNo = menuNo;
	}
	
}
