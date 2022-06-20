package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ClinicImg extends Domain {

	private static final long serialVersionUID = -5325915044614499910L;

	private Integer memNo;
	private Integer ino;
	private String gubun;
	private String attach;
	private String attachOrgName;

	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public Integer getIno() {
		return ino;
	}
	public void setIno(Integer ino) {
		this.ino = ino;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
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
