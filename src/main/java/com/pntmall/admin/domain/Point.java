package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Point extends Domain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7453777032743170297L;

	private Integer pno;
	private Integer memNo;
	private Integer curPoint;
	private Integer prevPoint;
	private Integer point;
	private String reason;
	private String edate;
	private Integer usePoint;
	private Integer balance;
	private String orderid;
	private String memId;
	private String memName;
	private String reasonName;
	private String gradeName;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public Integer getCurPoint() {
		return curPoint;
	}
	public void setCurPoint(Integer curPoint) {
		this.curPoint = curPoint;
	}
	public Integer getPrevPoint() {
		return prevPoint;
	}
	public void setPrevPoint(Integer prevPoint) {
		this.prevPoint = prevPoint;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public Integer getUsePoint() {
		return usePoint;
	}
	public void setUsePoint(Integer usePoint) {
		this.usePoint = usePoint;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	

}
