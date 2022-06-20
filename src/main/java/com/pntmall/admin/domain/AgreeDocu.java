package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class AgreeDocu extends Domain {

	private static final long serialVersionUID = -7554242286167098375L;
	private Integer adNo;
	private String attach;
	private String attachOrgName;

	public Integer getAdNo() {
		return adNo;
	}
	public void setAdNo(Integer adNo) {
		this.adNo = adNo;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getAttachOrgName() {
		return attachOrgName;
	}
	public void setAttachOrgName(String attachOrgName) {
		this.attachOrgName = attachOrgName;
	}


}
