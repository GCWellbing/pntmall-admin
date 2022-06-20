package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class HealthTopic extends Domain {
	private static final long serialVersionUID = -8341745628146948438L;

	private Integer healthNo;	//건강주제 순번
	private String title;	//건강주제
	private String img;	//이미지
	private String type;	//주제유형
	private String typeName;	//주제유형
	private Integer rank;	//전시순서
	private String status;	//상태
	private String statusName;	//상태

	public Integer getHealthNo() {
		return healthNo;
	}

	public void setHealthNo(Integer healthNo) {
		this.healthNo = healthNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
