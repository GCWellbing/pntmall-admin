package com.pntmall.admin.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.pntmall.common.type.SearchDomain;

public class MemberSearch extends SearchDomain {

	private static final long serialVersionUID = 3721059863020223880L;

	private Integer memNo;
	private String memId;
	private String name;
	private String gender;
	private Integer gradeNo;
	private String mtel;
	private String email;
	private String clinicId;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date fromCdate;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date toCdate;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date fromUdate;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date toUdate;
	private String clinicYn;
	private String seceType;


	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getMtel() {
		return mtel;
	}
	public void setMtel(String mtel) {
		this.mtel = mtel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	public Date getFromCdate() {
		return fromCdate;
	}
	public void setFromCdate(Date fromCdate) {
		this.fromCdate = fromCdate;
	}
	public Date getToCdate() {
		return toCdate;
	}
	public void setToCdate(Date toCdate) {
		this.toCdate = toCdate;
	}
	public Date getFromUdate() {
		return fromUdate;
	}
	public void setFromUdate(Date fromUdate) {
		this.fromUdate = fromUdate;
	}
	public Date getToUdate() {
		return toUdate;
	}
	public void setToUdate(Date toUdate) {
		this.toUdate = toUdate;
	}
	public String getClinicYn() {
		return clinicYn;
	}
	public void setClinicYn(String clinicYn) {
		this.clinicYn = clinicYn;
	}
	public String getSeceType() {
		return seceType;
	}
	public void setSeceType(String seceType) {
		this.seceType = seceType;
	}

}
