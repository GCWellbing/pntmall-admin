package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class SapProductSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8573129605616296489L;
	
	private String matnr;
	private String name;
	private String maktx;
	
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	
	
}
