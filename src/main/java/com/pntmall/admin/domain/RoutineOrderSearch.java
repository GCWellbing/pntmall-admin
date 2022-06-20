package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class RoutineOrderSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3292037800971218411L;

	private String sdate;
	private String edate;
	private String keyword;
	private String keytype;
	
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeytype() {
		return keytype;
	}
	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}
	
}
