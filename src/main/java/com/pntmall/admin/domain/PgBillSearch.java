package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class PgBillSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2545488503347629631L;

	private String sdate;
	private String edate;
	
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
