package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductNutrition extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7573369352551656011L;
	
	private Integer pno;
	private Integer nutritionNo;
	private String standard;
	private String content;
	private Integer rank;
	
	private String nutritionName;
	private String func;
	private String unit;
	private String unitName;
	
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
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNutritionName() {
		return nutritionName;
	}
	public void setNutritionName(String nutritionName) {
		this.nutritionName = nutritionName;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	

}
