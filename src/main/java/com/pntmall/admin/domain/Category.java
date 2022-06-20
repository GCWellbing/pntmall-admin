package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Category extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4863352954902173408L;
	
	private Integer cateNo;
	private Integer pcateNo;
	private String name;
	private Integer rank;
	private String remark;
	private String status;
	private String statusName;
	private String pname;
	
	public Integer getCateNo() {
		return cateNo;
	}
	public void setCateNo(Integer cateNo) {
		this.cateNo = cateNo;
	}
	public Integer getPcateNo() {
		return pcateNo;
	}
	public void setPcateNo(Integer pcateNo) {
		this.pcateNo = pcateNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
	
}
