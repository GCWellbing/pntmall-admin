package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class CouponSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3935508882147578070L;

	private String sdate;
	private String edate;
	private String status1;
	private String status2;
	private String title;
	private Integer gubun;
	private String memId;
	private Integer memNo;
	
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
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	
}
