package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Set extends Domain {

	private static final long serialVersionUID = 1037276587321436047L;

	private Integer sno;
	private String title;
	private String sdate;
	private String edate;
	private String summary;
	private String img;
	private String status;
	private String statusName;
	private String pcImgAlt;
	private String moImgAlt;

	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getPcImgAlt() {
		return pcImgAlt;
	}
	public void setPcImgAlt(String pcImgAlt) {
		this.pcImgAlt = pcImgAlt;
	}
	public String getMoImgAlt() {
		return moImgAlt;
	}
	public void setMoImgAlt(String moImgAlt) {
		this.moImgAlt = moImgAlt;
	}



}
