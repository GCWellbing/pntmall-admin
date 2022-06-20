package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class HealthExample extends Domain {
	private static final long serialVersionUID = -8421415316687169452L;

	private Integer healthNo;	//건강주제 순번
	private Integer exNo;	//보기 순번
	private String example;	//보기명
	private Integer point;	//배점
	private String productFun;	//제품기능
	private String report;	//제품기능 보고서
	private String warning;	//주의문구
	private Integer rank;	//전시 순서

	private Integer pno;	//상품번호
	private String pname;	//상품번호

	private Integer nutritionNo;	//성분
	private String nutritionName;	//성분


	public Integer getHealthNo() {
		return healthNo;
	}

	public void setHealthNo(Integer healthNo) {
		this.healthNo = healthNo;
	}

	public Integer getExNo() {
		return exNo;
	}

	public void setExNo(Integer exNo) {
		this.exNo = exNo;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getProductFun() {
		return productFun;
	}

	public void setProductFun(String productFun) {
		this.productFun = productFun;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getPno() {
		return pno;
	}

	public void setPno(Integer pno) {
		this.pno = pno;
	}

	public Integer getNutritionNo() {
		return nutritionNo;
	}

	public void setNutritionNo(Integer nutritionNo) {
		this.nutritionNo = nutritionNo;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getNutritionName() {
		return nutritionName;
	}

	public void setNutritionName(String nutritionName) {
		this.nutritionName = nutritionName;
	}
}
