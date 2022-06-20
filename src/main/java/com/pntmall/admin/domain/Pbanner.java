package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Pbanner extends Domain {

	private static final long serialVersionUID = 4482619798256205046L;

	private Integer bno;
	private String title;
	private String sdate;
	private String edate;
	private String remark;
	private String pcImg;
	private String moImg;
	private String status;
	private String statusName;
	private String pcImgAlt;
	private String moImgAlt;
	private Integer gubun;

	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPcImg() {
		return pcImg;
	}
	public void setPcImg(String pcImg) {
		this.pcImg = pcImg;
	}
	public String getMoImg() {
		return moImg;
	}
	public void setMoImg(String moImg) {
		this.moImg = moImg;
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
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}



}
