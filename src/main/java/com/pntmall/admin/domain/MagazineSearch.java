package com.pntmall.admin.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.pntmall.common.type.SearchDomain;

public class MagazineSearch extends SearchDomain {

	private static final long serialVersionUID = -283035103871873187L;

	private Integer mno;
	private String title;
	private String gubun;
	private String status;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date fromCdate;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date toCdate;

	public Integer getMno() {
		return mno;
	}
	public void setMno(Integer mno) {
		this.mno = mno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}





}
