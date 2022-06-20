package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class OrderAddr extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4763817850269851169L;
	
	private String orderid;
	private String sname;
	private String szip;
	private String saddr1;
	private String saddr2;
	private String smtel1;
	private String smtel2;
	private String stel1;
	private String stel2;
	private String msg;
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSzip() {
		return szip;
	}
	public void setSzip(String szip) {
		this.szip = szip;
	}
	public String getSaddr1() {
		return saddr1;
	}
	public void setSaddr1(String saddr1) {
		this.saddr1 = saddr1;
	}
	public String getSaddr2() {
		return saddr2;
	}
	public void setSaddr2(String saddr2) {
		this.saddr2 = saddr2;
	}
	public String getSmtel1() {
		return smtel1;
	}
	public void setSmtel1(String smtel1) {
		this.smtel1 = smtel1;
	}
	public String getSmtel2() {
		return smtel2;
	}
	public void setSmtel2(String smtel2) {
		this.smtel2 = smtel2;
	}
	public String getStel1() {
		return stel1;
	}
	public void setStel1(String stel1) {
		this.stel1 = stel1;
	}
	public String getStel2() {
		return stel2;
	}
	public void setStel2(String stel2) {
		this.stel2 = stel2;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
