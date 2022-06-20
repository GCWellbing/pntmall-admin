package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class BankCard extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4778126663898679013L;
	
	private Integer sno;
	private Integer gubun;
	private String name;
	private String sapCd;
	private String tossCd;
	
	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSapCd() {
		return sapCd;
	}
	public void setSapCd(String sapCd) {
		this.sapCd = sapCd;
	}
	public String getTossCd() {
		return tossCd;
	}
	public void setTossCd(String tossCd) {
		this.tossCd = tossCd;
	}
}
