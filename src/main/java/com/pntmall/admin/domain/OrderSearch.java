package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class OrderSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8420688253850372837L;

	private String sdate;
	private String edate;
	private String[] status;
	private String payType;
	private String orderGubun;
	private Integer gradeNo;
	private String device;
	private String firstOrderYn;
	private Integer spayAmt;
	private Integer epayAmt;
	private String keytype;
	private String keyword;
	private Integer memNo;
	private String clinicId;
	
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
	public String[] getStatus() {
		return status;
	}
	public void setStatus(String[] status) {
		this.status = status;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOrderGubun() {
		return orderGubun;
	}
	public void setOrderGubun(String orderGubun) {
		this.orderGubun = orderGubun;
	}
	public Integer getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getFirstOrderYn() {
		return firstOrderYn;
	}
	public void setFirstOrderYn(String firstOrderYn) {
		this.firstOrderYn = firstOrderYn;
	}
	public Integer getSpayAmt() {
		return spayAmt;
	}
	public void setSpayAmt(Integer spayAmt) {
		this.spayAmt = spayAmt;
	}
	public Integer getEpayAmt() {
		return epayAmt;
	}
	public void setEpayAmt(Integer epayAmt) {
		this.epayAmt = epayAmt;
	}
	public String getKeytype() {
		return keytype;
	}
	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	
}
