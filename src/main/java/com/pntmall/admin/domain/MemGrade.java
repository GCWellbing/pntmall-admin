package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class MemGrade extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3607579217264118576L;

	private Integer gradeNo;
	private String name;
	private Integer pointRate;
	private String remark;
	private String status;
	private String statusName;
	
	public Integer getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPointRate() {
		return pointRate;
	}
	public void setPointRate(Integer pointRate) {
		this.pointRate = pointRate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
