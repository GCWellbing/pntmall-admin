package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class RoutineOrder extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4006533988423973429L;

	private String orderid;
	private Integer memNo;
	private String oname;
	private String omtel1;
	private String omtel2;
	private String otel1;
	private String otel2;
	private String oemail;
	private String device;
	private String sdate;
	private Integer period;
	private Integer cnt;
	private String payType;
	private String billingkey;
	private String payLog;
	private String pname;
	private String memId;
	private String memName;
	private String memo;
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public String getOmtel1() {
		return omtel1;
	}
	public void setOmtel1(String omtel1) {
		this.omtel1 = omtel1;
	}
	public String getOmtel2() {
		return omtel2;
	}
	public void setOmtel2(String omtel2) {
		this.omtel2 = omtel2;
	}
	public String getOtel1() {
		return otel1;
	}
	public void setOtel1(String otel1) {
		this.otel1 = otel1;
	}
	public String getOtel2() {
		return otel2;
	}
	public void setOtel2(String otel2) {
		this.otel2 = otel2;
	}
	public String getOemail() {
		return oemail;
	}
	public void setOemail(String oemail) {
		this.oemail = oemail;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getBillingkey() {
		return billingkey;
	}
	public void setBillingkey(String billingkey) {
		this.billingkey = billingkey;
	}
	public String getPayLog() {
		return payLog;
	}
	public void setPayLog(String payLog) {
		this.payLog = payLog;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
