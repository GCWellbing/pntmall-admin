package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ReviewImg extends Domain {

	private static final long serialVersionUID = -6941254092179594022L;

	private Integer reviewNo;
	private String ino;
	private String attach;
	private String attachOrgName;
	public Integer getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(Integer reviewNo) {
		this.reviewNo = reviewNo;
	}
	public String getIno() {
		return ino;
	}
	public void setIno(String ino) {
		this.ino = ino;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getAttachOrgName() {
		return attachOrgName;
	}
	public void setAttachOrgName(String attachOrgName) {
		this.attachOrgName = attachOrgName;
	}





}
