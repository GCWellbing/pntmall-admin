package com.pntmall.admin.domain;

import java.util.Date;

import com.pntmall.common.type.Domain;

public class PaymentLog extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8294134654888387330L;
	
	private Integer plogNo;
	private String orderid;
	private Integer gubun;
	private String payType;
	private Integer payAmt;
	private String log;
	private Integer orderGubun;
	private String bank;
	private String accountNumber;
	private String businessNo;
	private String businessNo2;
	private String sapResult;
	private String sapMsg;
	private Date sapDate;
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Integer getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(Integer payAmt) {
		this.payAmt = payAmt;
	}
	public Integer getPlogNo() {
		return plogNo;
	}
	public void setPlogNo(Integer plogNo) {
		this.plogNo = plogNo;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getBusinessNo2() {
		return businessNo2;
	}
	public void setBusinessNo2(String businessNo2) {
		this.businessNo2 = businessNo2;
	}
	public Integer getOrderGubun() {
		return orderGubun;
	}
	public void setOrderGubun(Integer orderGubun) {
		this.orderGubun = orderGubun;
	}
	public String getSapResult() {
		return sapResult;
	}
	public void setSapResult(String sapResult) {
		this.sapResult = sapResult;
	}
	public String getSapMsg() {
		return sapMsg;
	}
	public void setSapMsg(String sapMsg) {
		this.sapMsg = sapMsg;
	}
	public Date getSapDate() {
		return sapDate;
	}
	public void setSapDate(Date sapDate) {
		this.sapDate = sapDate;
	}
	

}
