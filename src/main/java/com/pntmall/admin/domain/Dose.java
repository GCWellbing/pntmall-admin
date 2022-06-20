package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Dose extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1537142161017974623L;
	
	private Integer doseNo;
	private String img;
	private String content;
	private Integer rank;
	private String status;
	private String statusName;
	
	public Integer getDoseNo() {
		return doseNo;
	}
	public void setDoseNo(Integer doseNo) {
		this.doseNo = doseNo;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	

}
