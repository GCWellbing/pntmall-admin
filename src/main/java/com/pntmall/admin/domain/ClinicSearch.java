package com.pntmall.admin.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.pntmall.common.type.SearchDomain;

public class ClinicSearch extends SearchDomain {

	private static final long serialVersionUID = 9018773240941097711L;

	private Integer memNo;
	private String memId;
	private String clinicId;
	private String businessNo;
	private String clinicName;
	private String approve;
	private String[] approveArr;
	private String dispYn;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date fromCdateJoin;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date toCdateJoin;
	private String business2Yn;

	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}
	public Date getFromCdateJoin() {
		return fromCdateJoin;
	}
	public void setFromCdateJoin(Date fromCdateJoin) {
		this.fromCdateJoin = fromCdateJoin;
	}
	public Date getToCdateJoin() {
		return toCdateJoin;
	}
	public void setToCdateJoin(Date toCdateJoin) {
		this.toCdateJoin = toCdateJoin;
	}
	public String[] getApproveArr() {
		return approveArr;
	}
	public void setApproveArr(String[] approveArr) {
		this.approveArr = approveArr;
	}
	public String getBusiness2Yn() {
		return business2Yn;
	}
	public void setBusiness2Yn(String business2Yn) {
		this.business2Yn = business2Yn;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}


}
