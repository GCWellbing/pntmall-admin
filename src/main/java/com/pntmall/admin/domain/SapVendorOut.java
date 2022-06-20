package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class SapVendorOut extends Domain {

	private static final long serialVersionUID = 3323811310710330892L;

	private Integer memNo;
	private String pntResult;
	private String pntMsg;
	private String pntVdrCd;
	private String pntVdrNm;
	private String pntCtrCd;
	private String pntCtrNm;
	private String pntBizRegNo;

	public String getPntResult() {
		return pntResult;
	}
	public void setPntResult(String pntResult) {
		this.pntResult = pntResult;
	}
	public String getPntMsg() {
		return pntMsg;
	}
	public void setPntMsg(String pntMsg) {
		this.pntMsg = pntMsg;
	}
	public String getPntVdrCd() {
		return pntVdrCd;
	}
	public void setPntVdrCd(String pntVdrCd) {
		this.pntVdrCd = pntVdrCd;
	}
	public String getPntVdrNm() {
		return pntVdrNm;
	}
	public void setPntVdrNm(String pntVdrNm) {
		this.pntVdrNm = pntVdrNm;
	}
	public String getPntCtrCd() {
		return pntCtrCd;
	}
	public void setPntCtrCd(String pntCtrCd) {
		this.pntCtrCd = pntCtrCd;
	}
	public String getPntCtrNm() {
		return pntCtrNm;
	}
	public void setPntCtrNm(String pntCtrNm) {
		this.pntCtrNm = pntCtrNm;
	}
	public String getPntBizRegNo() {
		return pntBizRegNo;
	}
	public void setPntBizRegNo(String pntBizRegNo) {
		this.pntBizRegNo = pntBizRegNo;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}

}
