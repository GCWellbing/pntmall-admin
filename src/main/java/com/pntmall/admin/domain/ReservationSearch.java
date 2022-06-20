package com.pntmall.admin.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.pntmall.common.type.SearchDomain;

public class ReservationSearch extends SearchDomain {

	private static final long serialVersionUID = 2053774326560609474L;
	private Integer resNo;
	private Integer clinicMemNo;
	private String clinicMemId;
	private String clinicName;
	private String cate;
	private String status;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date fromDate;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date toDate;
	private String name;
	private String mtel;
	private String doctorPack;

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
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMtel() {
		return mtel;
	}
	public void setMtel(String mtel) {
		this.mtel = mtel;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getDoctorPack() {
		return doctorPack;
	}
	public void setDoctorPack(String doctorPack) {
		this.doctorPack = doctorPack;
	}
	public String getClinicMemId() {
		return clinicMemId;
	}
	public void setClinicMemId(String clinicMemId) {
		this.clinicMemId = clinicMemId;
	}

}
