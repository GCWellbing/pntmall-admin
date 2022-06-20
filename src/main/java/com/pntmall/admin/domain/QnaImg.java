package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class QnaImg extends Domain {

	private static final long serialVersionUID = 3321164738620493784L;
	private Integer qnaNo;
	private String ino;
	private String attach;
	private String attachOrgName;

	public Integer getQnaNo() {
		return qnaNo;
	}
	public void setQnaNo(Integer qnaNo) {
		this.qnaNo = qnaNo;
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
