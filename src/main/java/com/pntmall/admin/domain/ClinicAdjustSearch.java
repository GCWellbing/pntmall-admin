package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class ClinicAdjustSearch extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2664749820959801273L;
	
	private Integer year;
	private Integer quarter;
	private Integer year1;
	private Integer quarter1;
	private Integer year2;
	private Integer quarter2;
	private Integer status;
	private String memId;
	private String clinicName;
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getQuarter() {
		return quarter;
	}
	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public Integer getYear1() {
		return year1;
	}
	public void setYear1(Integer year1) {
		this.year1 = year1;
	}
	public Integer getQuarter1() {
		return quarter1;
	}
	public void setQuarter1(Integer quarter1) {
		this.quarter1 = quarter1;
	}
	public Integer getYear2() {
		return year2;
	}
	public void setYear2(Integer year2) {
		this.year2 = year2;
	}
	public Integer getQuarter2() {
		return quarter2;
	}
	public void setQuarter2(Integer quarter2) {
		this.quarter2 = quarter2;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	
}
