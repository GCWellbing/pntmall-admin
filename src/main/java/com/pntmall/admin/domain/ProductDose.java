package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class ProductDose extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9117084196507501759L;
	
	private Integer pno;
	private Integer doseNo;
	private Integer rank;
	private String content;
	
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getDoseNo() {
		return doseNo;
	}
	public void setDoseNo(Integer doseNo) {
		this.doseNo = doseNo;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
