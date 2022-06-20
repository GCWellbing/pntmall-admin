package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class NoticeImg extends Domain {

	private static final long serialVersionUID = -2354154232025033056L;
	private Integer noticeNo;
	private String ino;
	private String attach;
	private String attachOrgName;

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
	public Integer getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(Integer noticeNo) {
		this.noticeNo = noticeNo;
	}





}
