package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class OrderRefund extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1477790940267854295L;

	private Integer rno;
	private String orderid;
	private String bank;
	private String account;
	private String depositor;
	private Integer amt;
	private Integer point;
	private String reason;
	private String reason2;
	private String rejectReason;
	
	public Integer getRno() {
		return rno;
	}
	public void setRno(Integer rno) {
		this.rno = rno;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDepositor() {
		return depositor;
	}
	public void setDepositor(String depositor) {
		this.depositor = depositor;
	}
	public Integer getAmt() {
		return amt;
	}
	public void setAmt(Integer amt) {
		this.amt = amt;
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
	public String getReason2() {
		return reason2;
	}
	public void setReason2(String reason2) {
		this.reason2 = reason2;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
}
