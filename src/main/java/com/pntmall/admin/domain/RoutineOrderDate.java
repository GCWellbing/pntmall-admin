package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class RoutineOrderDate extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6365985622817151889L;

	private Integer dno;
	private String orderid;
	private String payDate;
	private String status;
	private String rorderid;
	private String statusName;
	
	public Integer getDno() {
		return dno;
	}
	public void setDno(Integer dno) {
		this.dno = dno;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRorderid() {
		return rorderid;
	}
	public void setRorderid(String rorderid) {
		this.rorderid = rorderid;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	
	
	
}
