package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class MemberCs extends Domain {

	private static final long serialVersionUID = 4313926529017450925L;

	private Integer memNo;
	private Integer cno;
	private String memo;
	private String status;
	private String statusName;

	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public Integer getCno() {
		return cno;
	}
	public void setCno(Integer cno) {
		this.cno = cno;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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

}
