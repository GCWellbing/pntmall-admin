package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Code extends Domain {

	/**
	 *
	 */
	private static final long serialVersionUID = 4435747097118870721L;

	private String code1;
	private String code2;
	private String name;
	private String remark;
	private Integer rank;
	private String status;
	private String sapCode;
	private String statusName;

	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getSapCode() {
		return sapCode;
	}
	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}


}
