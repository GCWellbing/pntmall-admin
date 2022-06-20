package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Reservation extends Domain {

	private static final long serialVersionUID = -1646160818122490100L;
	private Integer resNo;
	private Integer clinicMemNo;
	private String rdate;
	private String rtime;
	private String cate;
	private String mtel1;
	private String mtel2;
	private String content;
	private String reason;
	private String status;
	private String cartYn;
	private String clinicName;
	private String clinicMemId;
	private String cateName;
	private String statusName;
	private String pnos;
	private String memo;
	private String email;
	private Integer healthSeq;

	public Integer getResNo() {
		return resNo;
	}
	public void setResNo(Integer resNo) {
		this.resNo = resNo;
	}
	public Integer getClinicMemNo() {
		return clinicMemNo;
	}
	public void setClinicMemNo(Integer clinicMemNo) {
		this.clinicMemNo = clinicMemNo;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getRtime() {
		return rtime;
	}
	public void setRtime(String rtime) {
		this.rtime = rtime;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getMtel1() {
		return mtel1;
	}
	public void setMtel1(String mtel1) {
		this.mtel1 = mtel1;
	}
	public String getMtel2() {
		return mtel2;
	}
	public void setMtel2(String mtel2) {
		this.mtel2 = mtel2;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCartYn() {
		return cartYn;
	}
	public void setCartYn(String cartYn) {
		this.cartYn = cartYn;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getPnos() {
		return pnos;
	}
	public void setPnos(String pnos) {
		this.pnos = pnos;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getHealthSeq() {
		return healthSeq;
	}
	public void setHealthSeq(Integer healthSeq) {
		this.healthSeq = healthSeq;
	}
	public String getClinicMemId() {
		return clinicMemId;
	}
	public void setClinicMemId(String clinicMemId) {
		this.clinicMemId = clinicMemId;
	}


}
