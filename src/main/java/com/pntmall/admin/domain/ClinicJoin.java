package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ClinicJoin extends Domain {

	private static final long serialVersionUID = 3774984664989722596L;

	private Integer memNo;
	private Integer jno;
	private String approve;
	private String approveName;
	private String reason;
	private String sendMsg;
	private String chkMemNo;

	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public Integer getJno() {
		return jno;
	}
	public void setJno(Integer jno) {
		this.jno = jno;
	}
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSendMsg() {
		return sendMsg;
	}
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	public String getChkMemNo() {
		return chkMemNo;
	}
	public void setChkMemNo(String chkMemNo) {
		this.chkMemNo = chkMemNo;
	}


}
