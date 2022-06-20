package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Pexhibition extends Domain {

	private static final long serialVersionUID = 4517326012861511697L;

	private Integer seno;
	private String title;
	private String viewCnt;
	private String sdate;
	private String edate;
	private String summary;
	private String img;
	private String banner;
	private String pcDesc;
	private String moDesc;
	private String productYn;
	private String gnbYn;
	private String status;
	private String statusName;

	public Integer getSeno() {
		return seno;
	}
	public void setSeno(Integer seno) {
		this.seno = seno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(String viewCnt) {
		this.viewCnt = viewCnt;
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
	public String getPcDesc() {
		return pcDesc;
	}
	public void setPcDesc(String pcDesc) {
		this.pcDesc = pcDesc;
	}
	public String getMoDesc() {
		return moDesc;
	}
	public void setMoDesc(String moDesc) {
		this.moDesc = moDesc;
	}
	public String getProductYn() {
		return productYn;
	}
	public void setProductYn(String productYn) {
		this.productYn = productYn;
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
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getGnbYn() {
		return gnbYn;
	}
	public void setGnbYn(String gnbYn) {
		this.gnbYn = gnbYn;
	}

}
