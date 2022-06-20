package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class BbsImg extends Domain {

	private static final long serialVersionUID = -5430269210776514494L;
	private Integer bbsNo;
	private Integer ino;
	private String attach;
	private String attachOrgName;
	public Integer getBbsNo() {
		return bbsNo;
	}
	public void setBbsNo(Integer bbsNo) {
		this.bbsNo = bbsNo;
	}
	public Integer getIno() {
		return ino;
	}
	public void setIno(Integer ino) {
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
