package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class MenuAuth extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4935431004471064944L;
	
	private Integer adminNo;
	private Integer menuNo;
	
	public Integer getAdminNo() {
		return adminNo;
	}
	public void setAdminNo(Integer adminNo) {
		this.adminNo = adminNo;
	}
	public Integer getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(Integer menuNo) {
		this.menuNo = menuNo;
	}
	
	

}
