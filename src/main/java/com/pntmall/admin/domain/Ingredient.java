package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Ingredient extends Domain {

	private static final long serialVersionUID = 2566445807983044373L;

	private Integer ino;
	private String title;
	private Integer nutritionNo;
	private String nutritionName;
	private String img;
	private String content;
	private Integer rank;
	private String status;
	private String statusName;

	public Integer getIno() {
		return ino;
	}
	public void setIno(Integer ino) {
		this.ino = ino;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getNutritionNo() {
		return nutritionNo;
	}
	public void setNutritionNo(Integer nutritionNo) {
		this.nutritionNo = nutritionNo;
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
	public String getNutritionName() {
		return nutritionName;
	}
	public void setNutritionName(String nutritionName) {
		this.nutritionName = nutritionName;
	}

}
